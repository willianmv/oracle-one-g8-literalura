package com.challenge.literalura.service;

import com.challenge.literalura.dtos.GutendexAuthorDto;
import com.challenge.literalura.dtos.GutendexBookDto;
import com.challenge.literalura.model.Author;
import com.challenge.literalura.model.Book;
import com.challenge.literalura.repository.AuthorRepository;
import com.challenge.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final GutendexClient gutendexClient;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(GutendexClient gutendexClient, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.gutendexClient = gutendexClient;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book addBookToDataBase(String title) {
        Optional<GutendexBookDto> responseBook = gutendexClient.getBookJsonByTitle(title);
        if(responseBook.isEmpty()){
            System.out.println("--Livro não encontrado pela Gutendex API");
            return null;
        }

        GutendexBookDto bookDto = responseBook.get();
        Author author =  checkRegisteredAuthor(bookDto);
        return checkRegisteredBook(bookDto, author);
    }

    private Book checkRegisteredBook(GutendexBookDto bookDto,Author author) {
        return bookRepository.findByTitleAndAuthor(bookDto.title(), author)
                .orElseGet(() ->{
                    var newBook = new Book();
                    newBook.setTitle(bookDto.title());
                    newBook.setLanguage(bookDto.languages().getFirst());
                    newBook.setDownloads(bookDto.downloadCount());
                    newBook.setAuthor(author);
                    return bookRepository.save(newBook);
                });
    }

    private Author checkRegisteredAuthor(GutendexBookDto bookDto){
        GutendexAuthorDto authorDto= bookDto.authors().getFirst();
        return authorRepository.findByNameAndBirthYear(authorDto.name(), authorDto.birthYear())
                .orElseGet(() -> {
                    var newAuthor  = new Author();
                    newAuthor.setName(authorDto.name());
                    newAuthor.setBirthYear(authorDto.birthYear());
                    newAuthor.setDeathYear(authorDto.deathYear());
                    return authorRepository.save(newAuthor);
                });
    }


    public List<Book> getRegisteredBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksByLanguage(String language) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getLanguage().equalsIgnoreCase(language))
                .toList();
    }

    public List<Book> getTopBooks(int numberOfBooks) {
        return bookRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Book::getDownloads).reversed())
                .limit(numberOfBooks)
                .toList();
    }
}
