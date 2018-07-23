package com.garov.library.service.book.impl;

import com.garov.library.data.BookData;
import com.garov.library.exception.RentingException;
import com.garov.library.exception.WrongSearchParametersException;
import com.garov.library.model.Book;
import com.garov.library.model.Person;
import com.garov.library.model.Reader;
import com.garov.library.model.ReaderCard;
import com.garov.library.repository.BookRepository;
import com.garov.library.repository.PersonRepository;
import com.garov.library.repository.ReaderCardRepository;
import com.garov.library.repository.ReaderRepository;
import com.garov.library.service.book.BookService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class DefaultBookService implements BookService
{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ReaderCardRepository readerCardRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public Book save(BookData bookData)
    {
        Person author = personRepository.findByEgn(bookData.getAuthorEgn());
        return bookRepository.save(new Book(bookData.getIsbn(), bookData.getName(), author, null));
    }

    @Override
    public Book update(BookData bookData) throws WrongSearchParametersException
    {
        Set<Book> books = find(bookData);

        oneBookOnlyValidation(books, "More than 1 book found with the criteria provided. Cannot update multiple books");

        Book modifiedBook = books.iterator().next();
        bookSetModifiedFields(modifiedBook, bookData);
        return bookRepository.save(modifiedBook);
    }

    @Override
    public void delete(BookData bookData) throws WrongSearchParametersException
    {
        bookRepository.deleteAll(find(bookData));
    }

    @Override
    public Set<Book> find(BookData bookData) throws WrongSearchParametersException
    {
        Set<Book> books = new HashSet<>();

        if (bookData.getIsbn() != null)
        {
            Book foundBook = bookRepository.findByIsbn(bookData.getIsbn());
            if (foundBook != null)
            {
                books.add(foundBook);
            }
        }
        if (bookData.getAuthorEgn() != null)
        {
            books.addAll(bookRepository.findAllByAuthorEgn(bookData.getAuthorEgn()));
        }
        if (bookData.getName() != null)
        {
            books.addAll(bookRepository.findAllByName(bookData.getName()));
        }

        if (books.isEmpty())
        {
            throw new WrongSearchParametersException("No book found with the parameters provided.");
        }

        return books;
    }

    @Override
    public void rentBook(BookData bookData) throws WrongSearchParametersException, RentingException
    {
        ReaderCard card = readerCardRepository.findByNumber(bookData.getRentedByCardNumber());

        if (card == null)
        {
            throw new RentingException("Invalid card number. Cannot rent book.");
        }

        if (card.getValidTo().before(new Date()))
        {
            throw new RentingException("The reader's card is no longer valid! Cannot rent book.");
        }

        Set<Book> books = find(bookData);

        oneBookOnlyValidation(books,
                "More than 1 book found. Only one book at a time renting is supported. Please narrow down the search.");

        Book bookForRent = books.iterator().next();
        if (bookForRent.getRentedBy() != null)
        {
            throw new RentingException("This book is already rented. Cannot Rent.");
        }

        bookForRent.setRentedBy(card);
        bookRepository.save(bookForRent);
    }

    @Override
    public void returnBook(BookData bookData) throws WrongSearchParametersException, RentingException
    {
        Set<Book> books = find(bookData);

        oneBookOnlyValidation(books,
                "More than 1 book found. This only 1 book at a time check is supported. pLease narrow down the search.");

        Book bookForReturn = books.iterator().next();

        if (bookForReturn.getRentedBy() == null)
        {
            throw new RentingException("Cannot return a book that is not rented!");
        }

        bookForReturn.setRentedBy(null);

        bookRepository.save(bookForReturn);
    }

    @Override
    public Pair<Boolean, Reader> rentedBy(BookData bookData) throws WrongSearchParametersException
    {
        Set<Book> books = find(bookData);
        Pair<Boolean, Reader> rentedBy;

        oneBookOnlyValidation(books,
                "More than 1 book found. This only 1 book at a time check is supported. pLease narrow down the search.");

        Book rentedBook = books.iterator().next();

        if (rentedBook.getRentedBy() == null)
        {
            rentedBy = new Pair<>(false, null);
        }
        else
        {
            rentedBy = new Pair<>(true, readerRepository.findByCard(rentedBook.getRentedBy()));
        }

        return rentedBy;
    }

    @Override
    public List<Book> findAll()
    {
        return bookRepository.findAll();
    }

    private void oneBookOnlyValidation(Set<Book> books, String message) throws WrongSearchParametersException
    {
        if (books.size() > 1)
        {
            throw new WrongSearchParametersException(message);
        }
    }

    private void bookSetModifiedFields(Book modifiedBook, BookData bookData)
    {
        if (bookData.getName() != null)
        {
            modifiedBook.setName(bookData.getName());
        }
        if (bookData.getAuthorEgn() != null && !modifiedBook.getAuthor().getEgn().equals(bookData.getAuthorEgn()))
        {
            Person newAuthor = personRepository.findByEgn(bookData.getAuthorEgn());
            modifiedBook.setAuthor(newAuthor);
        }
    }
}
