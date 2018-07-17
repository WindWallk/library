package com.garov.library.controller;

import com.garov.library.data.ReaderData;
import com.garov.library.model.Reader;
import com.garov.library.service.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "library/reader")
public class ReaderController
{
    @Autowired
    private ReaderService readerService;

    @RequestMapping(method = RequestMethod.PUT)
    public String createUser(@RequestBody ReaderData readerData)
    {
        return "Reader with EGN " + readerService.save(readerData).getEgn() + " created!";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Reader> getPersons()
    {
        return readerService.findAllReaders();
    }
}
