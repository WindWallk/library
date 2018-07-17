package com.garov.library.service.reader.impl;

import com.garov.library.data.ReaderData;
import com.garov.library.model.Reader;
import com.garov.library.repository.ReaderRepository;
import com.garov.library.service.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DefaultReaderService implements ReaderService
{
    @Autowired
    private ReaderRepository readerRepository;

    @Override public Reader save(ReaderData readerData)
    {
        return readerRepository.save(
                new Reader(readerData.getFirstName(), readerData.getLastName(), readerData.getEgn(), readerData.getCardNumber(),
                        readerData.getValidTo()));
    }

    @Override public List<Reader> findAllReaders()
    {
        List<Reader> readers = new ArrayList<>();
        readers.addAll(readerRepository.findAll());
        return readers;
    }
}
