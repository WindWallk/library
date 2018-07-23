package com.garov.library.model;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person extends AbstractPerson
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    public Person()
    {

    }

    public Person(String firstName, String lastName, String egn)
    {
        super(firstName, lastName, egn);
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }
}
