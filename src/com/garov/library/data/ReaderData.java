package com.garov.library.data;

import java.util.Date;

public class ReaderData extends PersonData
{
    private long cardNumber;
    private Date validTo;
    
    public long getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber)
    {
        this.cardNumber = cardNumber;
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
