package com.garov.library.repository;

import com.garov.library.model.ReaderCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderCardRepository extends JpaRepository<ReaderCard, Long>
{
    ReaderCard findByNumber(long number);
}
