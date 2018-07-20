package com.garov.library.service.person;

import com.garov.library.data.PersonData;
import com.garov.library.model.Person;

import java.util.List;

public interface PersonService
{
    /**
     * Saves a person
     * @param personData the data that will be used to save a person
     * @return the saved person
     */
    Person save(PersonData personData);

    /**
     * Finds all persons in the db
     * @return all persons in the db
     */
    List<Person> findAllPersons();
}
