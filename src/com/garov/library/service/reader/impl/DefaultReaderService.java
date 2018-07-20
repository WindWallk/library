package com.garov.library.service.reader.impl;

import com.garov.library.data.ReaderData;
import com.garov.library.exception.WrongSearchParametersException;
import com.garov.library.model.Reader;
import com.garov.library.repository.ReaderRepository;
import com.garov.library.service.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Reader update(ReaderData readerData) throws WrongSearchParametersException
    {
        Set<Reader> readersFound = find(readerData);

        oneReaderOnlyValidation(readersFound, "There were multiple readers found with the information provided."
                + " Please use egn or card cumber to narrow down the search. Cannot update.");

        Reader modifiedReader = readersFound.iterator().next();
        readerSetModifiedFields(modifiedReader, readerData);

        return readerRepository.save(modifiedReader);
    }

    @Override
    public void delete(ReaderData readerData) throws WrongSearchParametersException
    {
        Set<Reader> readersFound = find(readerData);

        oneReaderOnlyValidation(readersFound, "There were multiple readers found with the information provided."
                + " Please use egn or card cumber to narrow down the search. Cannot delete.");

        Reader readerSetForDeletion = readersFound.iterator().next();

        readerRepository.delete(readerSetForDeletion);
    }

    @Override
    public Set<Reader> find(ReaderData readerData) throws WrongSearchParametersException
    {
        String firstName = readerData.getFirstName();
        String lastName = readerData.getLastName();
        String egn = readerData.getEgn();
        long cardNumber = readerData.getCardNumber();

        Set<Reader> readers = new HashSet<>();
        if (egn != null)
        {
            Reader readerFoundByEgn = readerRepository.findByEgn(egn);
            if (readerFoundByEgn != null)
            {
                readers.add(readerFoundByEgn);
            }
        }

        if (cardNumber != 0)
        {
            Reader readerFoundByCardNumber = readerRepository.findByEgn(egn);
            if (readerFoundByCardNumber != null)
            {
                readers.add(readerFoundByCardNumber);
            }
            readers.add(readerRepository.findByCardNumber(cardNumber));
        }

        if (firstName != null && lastName != null)
        {
            readers.addAll(readerRepository.findByFirstNameAndLastName(firstName, lastName));
        }

        if (readers.isEmpty())
        {
            throw new WrongSearchParametersException(
                    "There is no search supported with the provided parameters or no results found. Cannot find Reader.");
        }

        return readers;
    }

    @Override
    public List<Reader> findAll()
    {
        return readerRepository.findAll();
    }

    private void readerSetModifiedFields(Reader modifiedReader, ReaderData readerData)
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

    private void oneReaderOnlyValidation(Set<Reader> readersFound, String message) throws WrongSearchParametersException
    {
        if (readersFound.size() > 1)
        {
            throw new WrongSearchParametersException(message);
        }
    }
}
