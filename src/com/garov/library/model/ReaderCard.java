package com.garov.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reader_card")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReaderCard implements Serializable
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    private long number;

    @NotNull
    @Future
    private Date validTo;

    public ReaderCard()
    {

    }

    public ReaderCard(long number, Date validTo)
    {
        this.number = number;
        this.validTo = validTo;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getNumber()
    {
        return number;
    }

    public void setNumber(long number)
    {
        this.number = number;
    }

    public Date getValidTo()
    {
        return validTo;
    }

    public void setValidTo(Date validTo)
    {
        this.validTo = validTo;
    }


}
