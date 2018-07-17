package com.garov.library.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "reader")
public class Reader extends Human
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId
    private ReaderCard card;

    public Reader()
    {

    }

    public Reader(String firstName, String lastName, String egn, ReaderCard readerCard)
    {
        super(firstName, lastName, egn);
        this.card = readerCard;
    }

    public Reader(String firstName, String lastName, String egn, long number, Date validTo)
    {
        super(firstName, lastName, egn);
        this.card = new ReaderCard(number, validTo);
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public ReaderCard getReaderCard()
    {
        return card;
    }

    public void setReaderCard(ReaderCard readerCard)
    {
        this.card = readerCard;
    }
}
