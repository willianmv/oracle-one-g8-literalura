package com.challenge.literalura.repository;

import com.challenge.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameAndBirthYear(String name, Integer birthYear);

    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> findAllAuthorWithBooks();

    List<Author> findByNameContainingIgnoreCase(String name);

}
