package com.garov.library.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "book")
public class Book implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    private String isbn;

    @NotNull
    private String name;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private Person author;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "rented_by", nullable = true)
    private ReaderCard rentedBy;

    public Book()
    {
    }

    public Book(String isbn, String name, Person author, ReaderCard rentedBy)
    {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.rentedBy = rentedBy;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Person getAuthor()
    {
        return author;
    }

    public void setAuthor(Person author)
    {
        this.author = author;
    }

    public ReaderCard getRentedBy()
    {
        return rentedBy;
    }

    public void setRentedBy(ReaderCard rentedBy)
    {
        this.rentedBy = rentedBy;
    }
}
