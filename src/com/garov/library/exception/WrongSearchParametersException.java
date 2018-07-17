package com.garov.library.exception;

public class WrongSearchParametersException extends RuntimeException
{
    public WrongSearchParametersException(String message)
    {
        super(message);
    }
}
