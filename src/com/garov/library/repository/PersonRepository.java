package com.garov.library.repository;

import com.garov.library.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>
{
    Person findByEgn(String egn);
}
