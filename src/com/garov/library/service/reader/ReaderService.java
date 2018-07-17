package com.garov.library.service.reader;

import com.garov.library.data.ReaderData;
import com.garov.library.model.Reader;

import java.util.List;

public interface ReaderService
{
    Reader save(ReaderData readerData);

    List<Reader> findAllReaders();
}
