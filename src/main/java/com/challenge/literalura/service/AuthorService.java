package com.challenge.literalura.service;

import com.challenge.literalura.model.Author;
import com.challenge.literalura.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public List<Author> getAllAuthors() {
        return authorRepository.findAllAuthorWithBooks();
    }

    public List<Author> getLivingAuthorsAt(int year) {
        return authorRepository.findAll().stream()
                .filter(author -> author.getBirthYear() <= year && author.getDeathYear() > year)
                .toList();
    }

    public List<Author> getAuthorsByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }
}
