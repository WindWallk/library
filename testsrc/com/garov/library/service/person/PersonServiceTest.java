package com.garov.library.service.person;

import com.garov.library.data.PersonData;
import com.garov.library.model.Person;
import com.garov.library.IntegrationTest;
import org.flywaydb.test.annotation.FlywayTest;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PersonServiceTest extends IntegrationTest
{
    @Autowired
    private PersonService personService;

    @Test
    @FlywayTest
    public void testSavePerson()
    {
        PersonData personData = createPersonData("test", "test", "1234567890");

        assertEquals(personData.getEgn(), personService.save(personData).getEgn());
    }

    @Test(expected = ConstraintViolationException.class)
    @FlywayTest
    public void testSavePersonTooLongEgn()
    {
        PersonData personData = createPersonData("test", "test", "123456789011");
        personService.save(personData);
    }

    @Test(expected = ConstraintViolationException.class)
    @FlywayTest
    public void testSavePersonTooShortEgn()
    {
        PersonData personData = createPersonData("test", "test", "123456789");
        personService.save(personData);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @FlywayTest
    public void testSavePersonDuplicateEgn()
    {
        PersonData personData = createPersonData("test", "test", "1234567890");

        personService.save(personData);
        personService.save(personData);
    }

    @Test
    @FlywayTest
    public void testFindAllPersons()
    {
        PersonData personData1 = createPersonData("test", "test", "1234567890");
        PersonData personData2 = createPersonData("test", "test", "1234567891");

        List<Person> createdPersons = new ArrayList<>();
        createdPersons.add(personService.save(personData1));
        createdPersons.add(personService.save(personData2));

        assertEquals(2, personService.findAllPersons().size());
        assertThat(personService.findAllPersons(), IsIterableContainingInOrder.contains(createdPersons.toArray()));
    }

    private PersonData createPersonData(String firstName, String lastName, String egn)
    {
        PersonData personData = new PersonData();
        personData.setFirstName(firstName);
        personData.setLastName(lastName);
        personData.setEgn(egn);

        return personData;
    }
}