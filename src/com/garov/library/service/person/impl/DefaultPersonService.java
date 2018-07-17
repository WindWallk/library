package com.garov.library.service.person.impl;

import com.garov.library.data.PersonData;
import com.garov.library.model.Person;
import com.garov.library.repository.PersonRepository;
import com.garov.library.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DefaultPersonService implements PersonService
{
    @Autowired
    private PersonRepository personRepository;

    @Override public Person save(PersonData personData)
    {
        return personRepository.save(new Person(personData.getFirstName(), personData.getLastName(), personData.getEgn()));
    }

    @Override public List<Person> findAllPersons()
    {
        List<Person> persons = new ArrayList<>();
        persons.addAll(personRepository.findAll());

        return persons;
    }
}
