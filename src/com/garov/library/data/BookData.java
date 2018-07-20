package com.garov.library.data;

public class BookData
{
    private String name;
    private String isbn;
    private String authorEgn;
    private long rentedByCardNumber;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getAuthorEgn()
    {
        return authorEgn;
    }

    public void setAuthorEgn(String authorEgn)
    {
        this.authorEgn = authorEgn;
    }

    public long getRentedByCardNumber()
    {
        return rentedByCardNumber;
    }

    public void setRentedByCardNumber(long rentedByCardNumber)
    {
        this.rentedByCardNumber = rentedByCardNumber;
    }
}
