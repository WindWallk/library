package com.garov.library.service;

import com.garov.library.data.ReaderData;
import com.garov.library.model.Reader;
import com.garov.library.service.reader.ReaderService;
import org.flywaydb.test.annotation.FlywayTest;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 01, 01);
        ReaderData readerData = createReaderData("test", "test", "1234567890", 1234, calendar.getTime());

        Reader createdReader = readerService.save(readerData);
        assertEquals(readerData.getEgn(), createdReader.getEgn());
        assertEquals(readerData.getCardNumber(), createdReader.getReaderCard().getNumber());
    }


    @Test(expected = DataIntegrityViolationException.class)
    @FlywayTest
    public void testSaveReaderDuplicateCardNumbers()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 01, 01);

        ReaderData readerData1 = createReaderData("test", "test", "1234567890", 1234, calendar.getTime());
        ReaderData readerData2 = createReaderData("test", "test", "1234567891", 1234, calendar.getTime());
        readerService.save(readerData1);
        readerService.save(readerData2);
    }

    @Test
    @FlywayTest
    public void testFindAllReaders()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 01, 01);

        ReaderData readerData1 = createReaderData("test", "test", "1234567890", 1234, calendar.getTime());
        ReaderData readerData2 = createReaderData("test", "test", "1234567891", 123, calendar.getTime());

        List<Reader> createdReaders = new ArrayList<>();
        createdReaders.add(readerService.save(readerData1));
        createdReaders.add(readerService.save(readerData2));

        assertEquals(2, readerService.findAll().size());
        assertThat(readerService.findAll(), IsIterableContainingInOrder.contains(createdReaders.toArray()));
    }

    @Test
    @FlywayTest
    public void testDeleteReader()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 01, 01);
        ReaderData readerData = createReaderData("test", "test", "1234567890", 1234, calendar.getTime());

        readerService.save(readerData);
        readerService.delete(readerData);
    }

    @Test
    @FlywayTest
    public void testFindReader()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 01, 01);
        ReaderData readerData1 = createReaderData("test", "test", "1234567890", 1234, calendar.getTime());
        ReaderData readerData2 = createReaderData("test", "test", "1234567891", 12345, calendar.getTime());

        Reader reader1 = readerService.save(readerData1);
        Reader reader2 = readerService.save(readerData2);
        List<Reader> readers = new ArrayList<>();
        readers.add(reader1);
        readers.add(reader2);

        ReaderData searchFirstLastName = createReaderData("test", "test", null, 0, null);
        assertEquals(2, readerService.find(searchFirstLastName).size());
        assertThat(readerService.find(searchFirstLastName), IsIterableContainingInAnyOrder.containsInAnyOrder(readers.toArray()));

        ReaderData searchEgn = createReaderData(null, null, "1234567890", 0, null);
        assertEquals(1, readerService.find(searchEgn).size());
        assertEquals(reader1, readerService.find(searchEgn).get(0));

        ReaderData searchCardNumber = createReaderData(null, null, null, 12345, null);
        assertEquals(1, readerService.find(searchCardNumber).size());
        assertEquals(reader2, readerService.find(searchCardNumber).get(0));
    }

    @Test
    @FlywayTest
    public void testUpdateReader()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 01, 01);
        ReaderData readerData = createReaderData("test", "test", "1234567890", 1234, calendar.getTime());
        readerService.save(readerData);
        ReaderData updatedReader = createReaderData("pesho", null, null, 1234, null);
        assertEquals(updatedReader.getFirstName(), readerService.update(updatedReader).getFirstName());
    }

    private ReaderData createReaderData(String firstName, String lastName, String egn, long cardNumber, Date validTo)
    {
        ReaderData readerData = new ReaderData();
        readerData.setFirstName(firstName);
        readerData.setLastName(lastName);
        readerData.setEgn(egn);
        readerData.setCardNumber(cardNumber);
        readerData.setValidTo(validTo);

        return readerData;
    }
}
