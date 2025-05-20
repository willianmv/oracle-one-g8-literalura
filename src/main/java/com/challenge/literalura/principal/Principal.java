package com.challenge.literalura.principal;

import com.challenge.literalura.model.Author;
import com.challenge.literalura.model.Book;
import com.challenge.literalura.service.AuthorService;
import com.challenge.literalura.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class Principal {

    private final Scanner sc;
    private final BookService bookService;
    private final AuthorService authorService;

    public Principal(BookService bookService, AuthorService authorService) {
        this.sc = new Scanner(System.in);
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public void menu(){

        while(true){
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
                            6 - Listar Top livros mais baixados
                            7 - Pesquisar por autores registrados
                            
                            0 - Sair
                            
                            =================================================
                            """
            );

            System.out.print("Digite uma opção: ");
            int op = sc.nextInt();
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
                case 6 -> getTopBooksDownloaded();
                case 7 -> getAuthorsByName();
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
                """, book.getId(), book.getTitle(),
                    book.getAuthor().getName(), book.getAuthor().getBirthYear(), book.getAuthor().getDeathYear(),
                    book.getLanguage(), book.getDownloads());
        }
    }

    private void getRegisteredBooks() {
        List<Book> books = bookService.getRegisteredBooks();
        books.forEach(book -> {
            System.out.printf("""
                - Id: %d
                - Título: %s
                - Autor: %s
                - Idioma: %s
                - Quantidade de downloads: %d
                ------------------------------------------------------
                """, book.getId(), book.getTitle(),
                    book.getAuthor().getName(),
                    book.getLanguage(), book.getDownloads());
        });

    }

    private void getRegisteredAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        authors.forEach(author -> {
            System.out.printf("""
                    - Id: %d
                    - Nome: %s
                    - Ano de nascimento: %d
                    - Ano de falecimento: %d
                    - Livros:
                    """, author.getId(), author.getName(),
                    author.getBirthYear(), author.getDeathYear());

            if(author.getBooks().isEmpty()){
                System.out.println("   Nenhum livro registrado.");
            }else{
                for (Book book : author.getBooks()) {
                    System.out.printf("   * %s (Idioma: %s | Downloads: %d)\n",
                            book.getTitle(), book.getLanguage(), book.getDownloads());
                }
            }

            System.out.println("------------------------------------------------------");
        });
    }

    private void getLivingAuthorsByYear() {
        System.out.print("--Insira o ano que deseja pesquisar: ");
        int year = sc.nextInt();
        sc.nextLine();
        List<Author> authors = authorService.getLivingAuthorsAt(year);
        authors.forEach(author -> {
            System.out.println("Autor: "+ author.getName() +
                    "(Nasceu em:  " + author.getBirthYear() +
                    " - Faleceu em: "+ author.getDeathYear()+")");
        });
    }

    private void getRegisteredBooksByLanguage() {
        System.out.println("--Insira um idioma para realizar a busca: (pt, es, fr, en)");
        String language = sc.nextLine();
        List<Book> books = bookService.getAllBooksByLanguage(language);
        books.forEach(book -> {
            System.out.println("Livro: "+ book.getTitle() + " - Autor: "+ book.getAuthor().getName());
        });
    }

    private void getTopBooksDownloaded(){
        System.out.print("--Insira a quantidade de livros que deseja verificar: ");
        int number = sc.nextInt();
        sc.nextLine();
        List<Book> books = bookService.getTopBooks(number);
        System.out.println("--Classificação de livros por downloads - TOP: "+number);
        books.forEach(book -> {
            System.out.println("Downloads: "+book.getDownloads()+" - Livro: "+book.getTitle());
        });
    }

    private void getAuthorsByName(){
        System.out.print("--Insira o nome do autor: ");
        String name = sc.nextLine();
        List<Author> authors = authorService.getAuthorsByName(name);
        if(authors.isEmpty()){
            System.out.println("--Sem registros com o texto passado: "+name);
        } else{
            System.out.println("--Autores encontrados: ");
            authors.forEach(author -> {
                System.out.println("--Nome: "+author.getName()+
                        " - Nasceu em: "+author.getBirthYear()+
                        " - Morreu em: "+author.getDeathYear());
            });
        }
    }

}
