package com.garov.library.service;

import com.garov.library.data.ReaderData;
import com.garov.library.model.Reader;
import com.garov.library.service.reader.ReaderService;
import org.flywaydb.test.annotation.FlywayTest;
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

        assertEquals(2, readerService.findAllReaders().size());
        assertThat(readerService.findAllReaders(), IsIterableContainingInOrder.contains(createdReaders.toArray()));
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
