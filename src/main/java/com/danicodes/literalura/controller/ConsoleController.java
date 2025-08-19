package com.danicodes.literalura.controller;

import com.danicodes.literalura.model.Author;
import com.danicodes.literalura.model.Book;
import com.danicodes.literalura.service.CatalogService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleController {

    private final CatalogService catalog;

    public ConsoleController(CatalogService catalog) {
        this.catalog = catalog;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=== LiterAlura — Catálogo de Livros ===");
        while (true) {
            printMenu();
            String input = sc.nextLine().trim();
            switch (input) {
                case "1" -> searchAndSave(sc);
                case "2" -> listBooks();
                case "3" -> listAuthors();
                case "4" -> listAuthorsAlive(sc);
                case "5" -> listBooksByLanguage(sc);
                case "6" -> top10();
                case "0" -> {
                    System.out.println("Até mais!");
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1 - Buscar livro pelo título (API Gutendex) e salvar");
        System.out.println("2 - Listar livros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores vivos em um ano");
        System.out.println("5 - Listar livros por idioma (pt, en, es, fr)");
        System.out.println("6 - Top 10 livros por downloads");
        System.out.println("0 - Sair");
        System.out.print("> ");
    }

    private void searchAndSave(Scanner sc) {
        System.out.print("Digite o título do livro: ");
        String title = sc.nextLine().trim();
        Optional<Book> opt = catalog.fetchAndSaveByTitle(title);
        if (opt.isPresent()) {
            System.out.println("\nSalvo/atual encontrado: " + opt.get());
        } else {
            System.out.println("\nNenhum resultado encontrado para o título informado.");
        }
    }

    private void listBooks() {
        List<Book> books = catalog.listBooks();
        if (books.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        books.forEach(b -> System.out.println("- " + b));
    }

    private void listAuthors() {
        List<Author> authors = catalog.listAuthors();
        if (authors.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
            return;
        }
        authors.forEach(a -> {
            System.out.println("- " + a);
            a.getBooks().forEach(b -> System.out.println("    * " + b.getTitle()));
        });
    }

    private void listAuthorsAlive(Scanner sc) {
        try {
            System.out.print("Informe o ano (ex.: 1800): ");
            int year = Integer.parseInt(sc.nextLine().trim());
            List<Author> list = catalog.authorsAliveIn(year);
            if (list.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo nesse ano.");
            } else {
                list.forEach(a -> System.out.println("- " + a));
            }
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido.");
        }
    }

    private void listBooksByLanguage(Scanner sc) {
        System.out.print("Informe o idioma (pt, en, es, fr): ");
        String lang = sc.nextLine().trim().toLowerCase();
        List<Book> list = catalog.booksByLanguage(lang);
        if (list.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma.");
        } else {
            list.forEach(b -> System.out.println("- " + b));
        }
    }

    private void top10() {
        List<Book> list = catalog.top10ByDownloads();
        if (list.isEmpty()) {
            System.out.println("Sem dados suficientes (busque e salve livros primeiro).");
        } else {
            System.out.println("Top 10 por downloads:");
            list.forEach(b -> System.out.println("- " + b.getTitle() + " | " + b.getDownloadCount()));
        }
    }
}
