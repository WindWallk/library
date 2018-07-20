package com.garov.library.controller;

import com.garov.library.data.BookData;
import com.garov.library.exception.RentingException;
import com.garov.library.exception.WrongSearchParametersException;
import com.garov.library.model.Book;
import com.garov.library.model.Reader;
import com.garov.library.service.book.BookService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/library/book")
public class BookController
{
    @Autowired
    private BookService bookService;

    /**
     * Creates a book
     *
     * @param bookData the data used to create a book
     * @return the created book
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Book createBook(@RequestBody BookData bookData)
    {
        return bookService.save(bookData);
    }

    /**
     * Finds 1..n books by multiple params
     *
     * @param isbn      optional parameter which can be used to search a book
     * @param name      optional parameter which can be used to search a book
     * @param authorEgn optional parameter which can be used to search a book
     * @return the found books
     * @throws WrongSearchParametersException when no book was found
     */
    @RequestMapping(method = RequestMethod.GET)
    public Set<Book> getBooks(@RequestParam Optional<String> isbn,
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> authorEgn) throws WrongSearchParametersException
    {
        BookData bookData = new BookData();
        isbn.ifPresent(bookData::setIsbn);
        name.ifPresent(bookData::setName);
        authorEgn.ifPresent(bookData::setAuthorEgn);

        return bookService.find(bookData);
    }

    /**
     * Updates a book
     *
     * @param bookData the data used to find and update the book
     * @return the updated book
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found with the data provided
     */
    @RequestMapping(method = RequestMethod.POST)
    public Book updateBook(@RequestBody BookData bookData) throws WrongSearchParametersException
    {
        return bookService.update(bookData);
    }

    /**
     * Deletes a book
     *
     * @param bookData the data used to find and delete the book
     * @return a message to confirm the deletion of the book
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found with the data provided
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteBook(@RequestBody BookData bookData) throws WrongSearchParametersException
    {
        bookService.delete(bookData);
        return "Book deleted!";
    }

    /**
     * Rents a book
     *
     * @param bookData the data used to rent the book,
     *                 rentedByCardNumber parameter of bookData will be used to rent the book by a reader
     * @return a confirmation message that the book was rented
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found with the data provided
     * @throws RentingException               when the reader's card is no longer valid, or no reader card
     *                                        was found, or the book has already been rented
     */
    @RequestMapping(value = "/rent", method = RequestMethod.POST)
    public String rentBook(@RequestBody BookData bookData) throws WrongSearchParametersException, RentingException
    {
        bookService.rentBook(bookData);
        return "Book rented!";
    }

    /**
     * Finds if and who is renting a book
     *
     * @param isbn      optional parameter which can be used to find the book
     * @param name      optional parameter which can be used to find the book
     * @param authorEgn optional parameter which can be used to find the book
     * @return (true, Reader) if the book is rented or (false, null) if the book is not
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found
     */
    @RequestMapping(value = "/rent", method = RequestMethod.GET)
    public Pair<Boolean, Reader> isBookRented(@RequestParam Optional<String> isbn,
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> authorEgn) throws WrongSearchParametersException
    {
        BookData bookData = new BookData();
        isbn.ifPresent(bookData::setIsbn);
        name.ifPresent(bookData::setName);
        authorEgn.ifPresent(bookData::setAuthorEgn);

        return bookService.rentedBy(bookData);
    }

    /**
     * Returns a book
     *
     * @param bookData the data used to find and return the book
     * @return a confirmation message that the book was returned
     * @throws WrongSearchParametersException when there was no book found, or multiple books were found
     * @throws RentingException               when the book was not rented
     */
    @RequestMapping(value = "/return", method = RequestMethod.POST)
    public String returnBook(@RequestBody BookData bookData) throws WrongSearchParametersException, RentingException
    {
        bookService.returnBook(bookData);

        return "Book returned!";
    }

    /**
     * Finds all books in the db
     *
     * @return all books in the db
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Book> getAllBooks()
    {
        return bookService.findAll();
    }
}
