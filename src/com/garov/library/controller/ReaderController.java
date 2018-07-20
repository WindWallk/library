package com.garov.library.controller;

import com.garov.library.data.ReaderData;
import com.garov.library.exception.WrongSearchParametersException;
import com.garov.library.model.Reader;
import com.garov.library.service.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "library/reader")
public class ReaderController
{
    @Autowired
    private ReaderService readerService;

    /**
     * Creates a reader
     *
     * @param readerData the data used to create the reader
     * @return the created reader
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Reader createReader(@RequestBody ReaderData readerData)
    {
        return readerService.save(readerData);
    }

    /**
     * Updates a reader
     *
     * @param readerData the data used to find and update the reader
     * @return the updated reader
     * @throws WrongSearchParametersException when no reader was found, or multiple readers ware found
     */

    @RequestMapping(method = RequestMethod.POST)
    public Reader updateReader(@RequestBody ReaderData readerData) throws WrongSearchParametersException
    {
        return readerService.update(readerData);
    }

    /**
     * Finds 1..n readers by multiple params
     *
     * @param egn        optional param used to find the readers
     * @param firstName  optional param used to find the readers
     * @param lastName   optional param used to find the readers
     * @param cardNumber optional param used to find the readers
     * @return all readers found
     * @throws WrongSearchParametersException when no reader was found, or multiple readers ware found
     */
    @RequestMapping(method = RequestMethod.GET)
    public Set<Reader> getReaders(@RequestParam Optional<String> egn,
            @RequestParam Optional<String> firstName,
            @RequestParam Optional<String> lastName,
            @RequestParam Optional<Long> cardNumber) throws WrongSearchParametersException
    {
        ReaderData readerData = new ReaderData();
        firstName.ifPresent(readerData::setFirstName);
        lastName.ifPresent(readerData::setLastName);
        egn.ifPresent(readerData::setEgn);
        cardNumber.ifPresent(readerData::setCardNumber);
        return readerService.find(readerData);
    }

    /**
     * Deletes a reader
     *
     * @param readerData the data used to find and delete the reader
     * @return a confirmation message that the reader is deleted
     * @throws WrongSearchParametersException when no reader was found, or multiple readers ware found
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteReader(@RequestBody ReaderData readerData) throws WrongSearchParametersException
    {
        readerService.delete(readerData);
        return "Reader deleted!";
    }

    /**
     * Finds all readers in the db
     *
     * @return all readers in the db
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Reader> getAllReaders()
    {
        return readerService.findAll();
    }
}
