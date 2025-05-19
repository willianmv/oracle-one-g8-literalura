package com.challenge.literalura.principal;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class Principal {

    public void menu(){

        Scanner sc = new Scanner(System.in);
        int op = -1;

        while(op != 0){
            System.out.println(
                    """
                            =================================================
                                               LITERALURA
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

            switch (op){
                case 0:
                    System.out.println("ENCERRANDO...");
                    return;
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    getRegisteredBooks();
                    break;
                case 3:
                    getRegisteredAuthors();
                    break;
                case 4:
                    getLivingAuthorsByYear();
                    break;
                case 5:
                    getRegisteredBooksByLanguage();
                    break;
                default:
                    System.out.println("=================================================");
                    System.out.println("Entre com uma opção válida");
                    break;
            }

        }

    }

    private void searchBookByTitle() {

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
