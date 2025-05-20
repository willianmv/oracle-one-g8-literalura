package com.challenge.literalura.principal;

import com.challenge.literalura.model.Book;
import com.challenge.literalura.service.BookService;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class Principal {

    private final Scanner sc;
    private final BookService bookService;

    public Principal(BookService bookService) {
        this.sc = new Scanner(System.in);
        this.bookService = bookService;
    }

    public void menu(){

        int op = -1;

        while(op != 0){
            System.out.println(
                    """
                            =================================================
                                           CATÁLOGO LITERALURA
                            =================================================
                            
                            1 - Buscar livro pelo título (via Gutendex API)
                            2 - Listar livros registrados
                            3 - Listar autores registrados
                            4 - Listar autores vivos em determinado ano
                            5 - Listar livros de determinado idioma
                            
                            0 - Sair
                            
                            =================================================
                            """
            );

            System.out.print("Digite uma opção: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 0 -> {
                    System.out.println("ENCERRANDO...");
                    return;
                }
                case 1 -> searchBookByTitle();
                case 2 -> getRegisteredBooks();
                case 3 -> getRegisteredAuthors();
                case 4 -> getLivingAuthorsByYear();
                case 5 -> getRegisteredBooksByLanguage();
                default -> {
                    System.out.println("=================================================");
                    System.out.println("Entre com uma opção válida");
                }
            }

        }

    }

    private void searchBookByTitle() {
        System.out.print("--Insira o título do livro que deseja adicionar: ");
        String title = sc.nextLine();
        Book book = bookService.addBookToDataBase(title);
        if (book != null) {
            System.out.println("=================================================");
            System.out.printf("""
                LIVRO ENCONTRADO:
                - Id: %d
                - Título: %s
                - Autor:
                 - Nome: %s
                 - Ano de nascimento: %d
                 - Ano de falecimento: %d
                - Idioma: %s
                - Quantidade de downloads: %d
                """, book.getAuthor().getId(), book.getTitle(),
                    book.getAuthor().getName(), book.getAuthor().getBirthYear(), book.getAuthor().getDeathYear(),
                    book.getLanguage(), book.getDownloads());
        }
    }

    private void getRegisteredBooks() {

    }

    private void getRegisteredAuthors() {

    }

    private void getLivingAuthorsByYear() {

    }

    private void getRegisteredBooksByLanguage() {
    }

}
