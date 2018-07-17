package com.garov.library.controller;

import com.garov.library.data.PersonData;
import com.garov.library.model.Person;
import com.garov.library.repository.PersonRepository;
import com.garov.library.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/library/person")
public class PersonController
{
    @Autowired
    private PersonService personService;

    @RequestMapping(method = RequestMethod.PUT)
    public String createUser(@RequestBody PersonData personData)
    {
        return "Person with EGN " + personService.save(personData) + " created!";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Person> getPersons()
    {
        return personService.findAllPersons();
    }
}
