package com.garov.library.controller;

import com.garov.library.data.PersonData;
import com.garov.library.model.Person;
import com.garov.library.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/person")
public class PersonController
{
    @Autowired
    private PersonService personService;

    /**
     * Creates a person
     *
     * @param personData the data used to create the person
     * @return the created person
     */
    @PutMapping
    public Person createPerson(@RequestBody PersonData personData)
    {
        return personService.save(personData);
    }

    /**
     * Finds all persons in the db
     *
     * @return all persons in the db
     */
    @GetMapping(value = "/all")
    public List<Person> getAllPersons()
    {
        return personService.findAllPersons();
    }
}
