package com.garov.library.controller;

import com.garov.library.data.ReaderData;
import com.garov.library.model.Reader;
import com.garov.library.service.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "library/reader")
public class ReaderController
{
    @Autowired
    private ReaderService readerService;

    @RequestMapping(method = RequestMethod.PUT)
    public String createReader(@RequestBody ReaderData readerData)
    {
        return "Reader with EGN " + readerService.save(readerData).getEgn() + " created!";
    }

    @RequestMapping(method = RequestMethod.POST)
    public Reader updateReader(@RequestBody ReaderData readerData)
    {
        return readerService.update(readerData);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Reader> getReaders(@RequestParam Optional<String> egn,
            @RequestParam Optional<String> firstName,
            @RequestParam Optional<String> lastName,
            @RequestParam Optional<Long> cardNumber)
    {
        ReaderData readerData = new ReaderData();
        firstName.ifPresent(readerData::setFirstName);
        lastName.ifPresent(readerData::setLastName);
        egn.ifPresent(readerData::setEgn);
        cardNumber.ifPresent(readerData::setCardNumber);
        return readerService.find(readerData);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteReader(@RequestBody ReaderData readerData)
    {
        readerService.delete(readerData);
        return "Reader deleted!";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Reader> getAllReaders()
    {
        return readerService.findAll();
    }
}
