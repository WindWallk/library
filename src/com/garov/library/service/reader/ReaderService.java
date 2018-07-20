package com.garov.library.service.reader;

import com.garov.library.data.ReaderData;
import com.garov.library.exception.WrongSearchParametersException;
import com.garov.library.model.Reader;

import java.util.List;
import java.util.Set;

public interface ReaderService
{
    /**
     * Saves a reader
     * @param readerData the data used to save the reader
     * @return the saved reader
     */
    Reader save(ReaderData readerData);

    /**
     * Updates a reader
     * @param readerData the data used to find the reader and update it. You can only update the first and last name and
     * the validity of the reader's card
     * @return the updated reader
     * @throws WrongSearchParametersException when no reader was found or multiple readers were found
     */
    Reader update(ReaderData readerData) throws WrongSearchParametersException;

    /**
     * Deletes a user
     * @param readerData the data used to find and delete the reader
     * @throws WrongSearchParametersException when no reader was found or multiple readers were found
     */
    void delete(ReaderData readerData) throws WrongSearchParametersException;

    /**
     * Finds 1..n readers
     * @param readerData the data used to search
     * @return all found books
     * @throws WrongSearchParametersException when no readers were found
     */
    Set<Reader> find(ReaderData readerData) throws WrongSearchParametersException;

    /**
     * Finds all readers in the db
     * @return all readers in the db
     */
    List<Reader> findAll();
}
