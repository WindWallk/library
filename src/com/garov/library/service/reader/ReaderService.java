package com.garov.library.service.reader;

import com.garov.library.data.ReaderData;
import com.garov.library.exception.DeleteException;
import com.garov.library.exception.UpdateException;
import com.garov.library.exception.WrongSearchParametersException;
import com.garov.library.model.Reader;

import java.util.List;

public interface ReaderService
{
    Reader save(ReaderData readerData);

    Reader update(ReaderData readerData) throws UpdateException;

    void delete(ReaderData readerData) throws DeleteException;

    List<Reader> find(ReaderData readerData) throws WrongSearchParametersException;

    List<Reader> findAll();
}
