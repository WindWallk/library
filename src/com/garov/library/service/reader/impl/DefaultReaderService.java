package com.garov.library.service.reader.impl;

import com.garov.library.data.ReaderData;
import com.garov.library.exception.DeleteException;
import com.garov.library.exception.UpdateException;
import com.garov.library.exception.WrongSearchParametersException;
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

    @Override
    public Reader save(ReaderData readerData)
    {
        return readerRepository.save(
                new Reader(readerData.getFirstName(), readerData.getLastName(), readerData.getEgn(), readerData.getCardNumber(),
                        readerData.getValidTo()));
    }

    @Override
    public Reader update(ReaderData readerData) throws UpdateException
    {
        List<Reader> readersFound;

        try
        {
            readersFound = find(readerData);
        }
        catch (WrongSearchParametersException ex)
        {
            throw new UpdateException("There was no reader found with the information provided. Cannot update.");
        }

        if (readersFound.size() > 1)
        {
            throw new UpdateException("There were multiple readers found with the information provided."
                    + " Please use egn or card cumber to narrow down the search. Cannot update.");
        }

        if (readersFound.size() == 0)
        {
            throw new UpdateException("No reader found with these parameters. Cannot update.");
        }

        Reader modifiedReader = readersFound.get(0);
        readerSetModifiedFields(modifiedReader, readerData);

        return readerRepository.save(modifiedReader);
    }

    @Override
    public void delete(ReaderData readerData) throws DeleteException
    {
        List<Reader> readersFound;

        try
        {
            readersFound = find(readerData);
        }
        catch (WrongSearchParametersException ex)
        {
            throw new DeleteException("There was no reader found with the information provided. Cannot delete.");
        }

        if (readersFound.size() > 1)
        {
            throw new DeleteException("There were multiple readers found with the information provided."
                    + " Please use egn or card cumber to narrow down the search. Cannot delete.");
        }

        if (readersFound.size() == 0)
        {
            throw new DeleteException("No reader found with these parameters. Cannot delete.");
        }

        Reader readerSetForDeletion = readersFound.get(0);

        readerRepository.delete(readerSetForDeletion);
    }

    @Override
    public List<Reader> find(ReaderData readerData) throws WrongSearchParametersException
    {
        String firstName = readerData.getFirstName();
        String lastName = readerData.getLastName();
        String egn = readerData.getEgn();
        long cardNumber = readerData.getCardNumber();

        List<Reader> readers = new ArrayList<>();
        if (egn != null)
        {
            readers = readerRepository.findByEgn(egn);
        }

        if (cardNumber != 0)
        {
            readers = readerRepository.findAllByCardNumber(cardNumber);
        }

        if (firstName != null && lastName != null)
        {
            readers = readerRepository.findByFirstNameAndLastName(firstName, lastName);
        }

        if(readers.isEmpty())
        {
            throw new WrongSearchParametersException(
                    "There is no search supported with the provided parameters or no results found. Cannot find Reader.");
        }

        return readers;
    }

    @Override
    public List<Reader> findAll()
    {
        List<Reader> readers = new ArrayList<>();
        readers.addAll(readerRepository.findAll());
        return readers;
    }

    protected void readerSetModifiedFields(Reader modifiedReader, ReaderData readerData)
    {
        if (readerData.getFirstName() != null)
        {
            modifiedReader.setFirstName(readerData.getFirstName());
        }
        if (readerData.getLastName() != null)
        {
            modifiedReader.setLastName(readerData.getLastName());
        }
        if (readerData.getValidTo() != null)
        {
            modifiedReader.getReaderCard().setValidTo(readerData.getValidTo());
        }
    }
}
