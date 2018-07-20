package com.garov.library.service.book;

import com.garov.library.IntegrationTest;
import com.garov.library.data.BookData;
import com.garov.library.exception.RentingException;
import com.garov.library.exception.WrongSearchParametersException;
import com.garov.library.model.Book;
import com.garov.library.model.Person;
import com.garov.library.model.Reader;
import com.garov.library.repository.PersonRepository;
import com.garov.library.repository.ReaderRepository;
import javafx.util.Pair;
import org.flywaydb.test.annotation.FlywayTest;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BookServiceTest extends IntegrationTest
{
    @Autowired
    private BookService bookService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ReaderRepository readerRepository;

    private Reader testReader;
    private Person testAuthor;
    private BookData bookData1;

    @Before
    public void before()
    {
        bookData1 = createBookData("test book 1", "123-456-789", "1234567890", 0);
        testAuthor = personRepository.save(new Person("test", "test", "1234567890"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        testReader = readerRepository.save(new Reader("testR", "testR", "1234567891", 123, calendar.getTime()));
    }

    @Test
    @FlywayTest
    public void testSaveBook()
    {
        Book createdBook = bookService.save(bookData1);

        assertEquals(bookData1.getName(), createdBook.getName());
        assertEquals(bookData1.getIsbn(), createdBook.getIsbn());
        assertEquals(testAuthor, createdBook.getAuthor());
    }

    @Test
    @FlywayTest
    public void testUpdateBook() throws WrongSearchParametersException
    {
        bookService.save(bookData1);

        BookData modifiedBook = createBookData("changed name", "123-456-789", null, 0);

        assertEquals("changed name", bookService.update(modifiedBook).getName());
    }

    @Test
    @FlywayTest
    public void testDeleteBook() throws WrongSearchParametersException
    {
        bookService.save(bookData1);

        BookData deletionData = createBookData(null, "123-456-789", null, 0);

        bookService.delete(deletionData);
    }

    @Test
    @FlywayTest
    public void testFindBook() throws WrongSearchParametersException
    {
        Set<Book> foundBooks;
        List<Book> books = new ArrayList<>();

        Book book1 = bookService.save(bookData1);
        BookData bookData2 = createBookData("test book 2", "123-456-780", "1234567890", 0);
        Book book2 = bookService.save(bookData2);

        books.add(book1);
        books.add(book2);

        BookData searchByName = createBookData("test book 1", null, null, 0);
        foundBooks = bookService.find(searchByName);
        assertEquals(1, foundBooks.size());
        assertEquals(book1, foundBooks.iterator().next());

        BookData searchByIsbn = createBookData(null, "123-456-780", null, 0);
        foundBooks = bookService.find(searchByIsbn);
        assertEquals(1, foundBooks.size());
        assertEquals(book2, foundBooks.iterator().next());

        BookData searchMultipleParamsSameBook = createBookData("test book 1", "123-456-789", null, 0);
        foundBooks = bookService.find(searchMultipleParamsSameBook);
        assertEquals(1, foundBooks.size());
        assertEquals(book1, foundBooks.iterator().next());

        BookData searchMultipleParamsDifferentBooks = createBookData("test book 2", "123-456-789", null, 0);
        foundBooks = bookService.find(searchMultipleParamsDifferentBooks);
        assertEquals(2, foundBooks.size());
        assertThat(foundBooks, IsIterableContainingInAnyOrder.containsInAnyOrder(books.toArray()));

        BookData searchByAuthorEgn = createBookData(null, null, "1234567890", 0);
        foundBooks = bookService.find(searchByAuthorEgn);
        assertEquals(2, foundBooks.size());
        assertThat(foundBooks, IsIterableContainingInAnyOrder.containsInAnyOrder(books.toArray()));
    }

    @Test(expected = WrongSearchParametersException.class)
    @FlywayTest
    public void testNoBooksFound() throws WrongSearchParametersException
    {
        bookService.save(bookData1);

        BookData searchByName = createBookData("test", null, null, 0);
        bookService.find(searchByName);
    }

    @Test
    @FlywayTest
    public void testRentBook() throws WrongSearchParametersException, RentingException
    {
        rentBook(123);
    }

    @Test(expected = RentingException.class)
    @FlywayTest
    public void testRentBookWithInvalidCardNumber() throws WrongSearchParametersException, RentingException
    {
        rentBook(1234);
    }

    @Test
    @FlywayTest
    public void testReturnBook() throws WrongSearchParametersException, RentingException
    {
        rentBook(123);
        BookData dataForReturning = createBookData(null, "123-456-789", null, 0);
        bookService.returnBook(dataForReturning);
    }

    @Test(expected = RentingException.class)
    @FlywayTest
    public void testReturnNotRentedBook() throws WrongSearchParametersException, RentingException
    {
        bookService.save(bookData1);
        BookData dataForReturning = createBookData(null, "123-456-789", null, 0);
        bookService.returnBook(dataForReturning);
    }

    @Test
    @FlywayTest
    public void testRentedByWithRentedBook() throws WrongSearchParametersException, RentingException
    {
        Pair<Boolean, Reader> rentedByExpected = new Pair<>(true, testReader);
        rentBook(123);

        BookData dataForRentedBook = createBookData(null, "123-456-789", null, 0);
        Pair<Boolean, Reader> rentedByActual = bookService.rentedBy(dataForRentedBook);
        assertEquals(rentedByExpected.getKey(), rentedByActual.getKey());
        assertEquals(rentedByExpected.getValue(), rentedByExpected.getValue());
    }

    private void rentBook(long cardNumber) throws WrongSearchParametersException, RentingException
    {
        bookService.save(bookData1);
        BookData dataForRenting = createBookData(null, "123-456-789", null, cardNumber);
        bookService.rentBook(dataForRenting);
    }

    private BookData createBookData(String name, String isbn, String authorEgn, long rentedByCardNumber)
    {
        BookData bookData = new BookData();
        bookData.setAuthorEgn(authorEgn);
        bookData.setIsbn(isbn);
        bookData.setName(name);
        bookData.setRentedByCardNumber(rentedByCardNumber);

        return bookData;
    }
}
