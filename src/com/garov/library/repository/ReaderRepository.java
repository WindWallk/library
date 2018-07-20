package com.garov.library.repository;

import com.garov.library.model.Reader;
import com.garov.library.model.ReaderCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Long>
{
    Reader findByEgn(String egn);

    List<Reader> findByFirstNameAndLastName(String firstName, String lastName);

    Reader findByCardNumber(long number);

    Reader findByCard(ReaderCard card);
}
