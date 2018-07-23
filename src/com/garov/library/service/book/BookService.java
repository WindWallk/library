package com.garov.library.service.book;

import com.garov.library.data.BookData;
import com.garov.library.exception.RentingException;
import com.garov.library.exception.WrongSearchParametersException;
import com.garov.library.model.Book;
import com.garov.library.model.Reader;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;

/**
 * This service provides all logic that can be applied to the books
 */
public interface BookService
{
    /**
     * Saves a single book
     *
     * @param bookData the data used to save the book
     * @return the saved book
     */
    Book save(BookData bookData);

    /**
     * Updates a single book
     *
     * @param bookData the data used to find the book and the changed data, you can only change the author
     *                 and the name of the book
     * @return the updated book
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found with the data provided
     */
    Book update(BookData bookData) throws WrongSearchParametersException;

    /**
     * Deletes a single book
     *
     * @param bookData the data used to find the book and deleted
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found with the data provided
     */
    void delete(BookData bookData) throws WrongSearchParametersException;

    /**
     * Finds 1..n books
     *
     * @param bookData the data used to search
     * @return all found books
     * @throws WrongSearchParametersException when there were no books found
     */
    Set<Book> find(BookData bookData) throws WrongSearchParametersException;

    /**
     * Rents a single book by a Reader based on reader card
     *
     * @param bookData the data used to rent the book,
     *                 rentedByCardNumber parameter of bookData will be used to rent the book by a reader
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found
     * @throws RentingException               when the reader's card is no longer valid, or no reader card
     *                                        was found, or the book has already been rented
     */
    void rentBook(BookData bookData) throws WrongSearchParametersException, RentingException;

    /**
     * Return a rented book
     *
     * @param bookData the data used to find the book
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found
     * @throws RentingException               when the found book was not rented.
     */
    void returnBook(BookData bookData) throws WrongSearchParametersException, RentingException;

    /**
     * Finds if and who is renting a book
     *
     * @param bookData the data used to find the book
     * @return (true, Reader) if the book is rented or (false, null) if the book is not
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found
     */
    Pair<Boolean, Reader> rentedBy(BookData bookData) throws WrongSearchParametersException;

    /**
     * Finds all books in the db
     *
     * @return all books in the db
     */
    List<Book> findAll();
}
