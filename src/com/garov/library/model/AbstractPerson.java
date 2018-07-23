package com.garov.library.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractPerson implements Serializable
{
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String lastName;

    @NotNull
    @Column(unique = true)
    @Length(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]*$")
    private String egn;

    public AbstractPerson()
    {

    }

    public AbstractPerson(String firstName, String lastName, String egn)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.egn = egn;
    }

    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEgn()
    {
        return egn;
    }

    public void setEgn(String egn)
    {
        this.egn = egn;
    }
}
