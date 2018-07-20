package com.garov.library.repository;

import com.garov.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>
{
    Book findByIsbn(String isbn);

    List<Book> findAllByName(String name);

    List<Book> findAllByAuthorEgn(String egn);
}
