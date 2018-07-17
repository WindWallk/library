package com.garov.library.repository;

import com.garov.library.model.Reader;
import com.garov.library.model.ReaderCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Long>
{
    List<Reader> findByEgn(String egn);

    List<Reader> findByFirstNameAndLastName(String firstName, String lastName);

    List<Reader> findAllByCardNumber(long number);
}
