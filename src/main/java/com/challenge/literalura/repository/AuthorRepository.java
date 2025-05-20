package com.challenge.literalura.repository;

import com.challenge.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameAndBirthYear(String name, Integer birthYear);

}
