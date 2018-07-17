package com.garov.library.service.person;

import com.garov.library.data.PersonData;
import com.garov.library.model.Person;

import java.util.List;

public interface PersonService
{
    Person save(PersonData personData);

    List<Person> findAllPersons();
}
