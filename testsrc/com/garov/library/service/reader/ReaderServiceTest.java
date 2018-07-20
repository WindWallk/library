package com.garov.library.service.reader;

import com.garov.library.data.ReaderData;
import com.garov.library.exception.WrongSearchParametersException;
import com.garov.library.model.Reader;
import com.garov.library.IntegrationTest;
import org.flywaydb.test.annotation.FlywayTest;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ReaderServiceTest extends IntegrationTest
{
    @Autowired
    private ReaderService readerService;

    @Test
    @FlywayTest
    public void testSaveReader()
    {
        ReaderData readerData = createReaderData("test", "test", "1234567890", 1234);

        Reader createdReader = readerService.save(readerData);
        assertEquals(readerData.getEgn(), createdReader.getEgn());
        assertEquals(readerData.getCardNumber(), createdReader.getReaderCard().getNumber());
    }


    @Test(expected = DataIntegrityViolationException.class)
    @FlywayTest
    public void testSaveReaderDuplicateCardNumbers()
    {
        ReaderData readerData1 = createReaderData("test", "test", "1234567890", 1234);
        ReaderData readerData2 = createReaderData("test", "test", "1234567891", 1234);
        readerService.save(readerData1);
        readerService.save(readerData2);
    }

    @Test
    @FlywayTest
    public void testFindAllReaders()
    {
        ReaderData readerData1 = createReaderData("test", "test", "1234567890", 1234);
        ReaderData readerData2 = createReaderData("test", "test", "1234567891", 123);

        List<Reader> createdReaders = new ArrayList<>();
        createdReaders.add(readerService.save(readerData1));
        createdReaders.add(readerService.save(readerData2));

        assertEquals(2, readerService.findAll().size());
        assertThat(readerService.findAll(), IsIterableContainingInOrder.contains(createdReaders.toArray()));
    }

    @Test
    @FlywayTest
    public void testDeleteReader() throws WrongSearchParametersException
    {
        ReaderData readerData = createReaderData("test", "test", "1234567890", 1234);

        readerService.save(readerData);
        readerService.delete(readerData);
    }

    @Test
    @FlywayTest
    public void testFindReader() throws WrongSearchParametersException
    {
        ReaderData readerData1 = createReaderData("test", "test", "1234567890", 1234);
        ReaderData readerData2 = createReaderData("test", "test", "1234567891", 12345);

        Reader reader1 = readerService.save(readerData1);
        Reader reader2 = readerService.save(readerData2);
        List<Reader> readers = new ArrayList<>();
        readers.add(reader1);
        readers.add(reader2);

        ReaderData searchFirstLastName = createReaderData("test", "test", null, 0);
        assertEquals(2, readerService.find(searchFirstLastName).size());
        assertThat(readerService.find(searchFirstLastName), IsIterableContainingInAnyOrder.containsInAnyOrder(readers.toArray()));

        ReaderData searchEgn = createReaderData(null, null, "1234567890", 0);
        assertEquals(1, readerService.find(searchEgn).size());
        assertEquals(reader1, readerService.find(searchEgn).iterator().next());

        ReaderData searchCardNumber = createReaderData(null, null, null, 12345);
        assertEquals(1, readerService.find(searchCardNumber).size());
        assertEquals(reader2, readerService.find(searchCardNumber).iterator().next());
    }

    @Test
    @FlywayTest
    public void testUpdateReader() throws WrongSearchParametersException
    {
        ReaderData readerData = createReaderData("test", "test", "1234567890", 1234);
        readerService.save(readerData);
        ReaderData updatedReader = createReaderData("pesho", null, null, 1234);
        assertEquals(updatedReader.getFirstName(), readerService.update(updatedReader).getFirstName());
    }

    private ReaderData createReaderData(String firstName, String lastName, String egn, long cardNumber)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);

        ReaderData readerData = new ReaderData();
        readerData.setFirstName(firstName);
        readerData.setLastName(lastName);
        readerData.setEgn(egn);
        readerData.setCardNumber(cardNumber);
        readerData.setValidTo(calendar.getTime());

        return readerData;
    }
}
