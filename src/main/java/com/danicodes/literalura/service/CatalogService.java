package com.danicodes.literalura.service;

import com.danicodes.literalura.model.Author;
import com.danicodes.literalura.model.Book;
import com.danicodes.literalura.repository.AuthorRepository;
import com.danicodes.literalura.repository.BookRepository;
import com.danicodes.literalura.service.dto.BookResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CatalogService {

    private final AuthorRepository authorRepo;
    private final BookRepository bookRepo;
    private final GutendexClient gutendex;

    public CatalogService(AuthorRepository authorRepo, BookRepository bookRepo, GutendexClient gutendex) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.gutendex = gutendex;
    }

    @Transactional
    public Optional<Book> fetchAndSaveByTitle(String title) {
        Optional<BookResult> opt = gutendex.searchFirstByTitle(title);
        if (opt.isEmpty()) return Optional.empty();

        BookResult br = opt.get();
        String language = (br.languages != null && !br.languages.isEmpty()) ? br.languages.get(0) : "??";
        int downloads = br.download_count != null ? br.download_count : 0;

        // Choose first author if present
        String authorName = "Autor Desconhecido";
        Integer birth = null, death = null;
        if (br.authors != null && !br.authors.isEmpty()) {
            BookResult.Person p = br.authors.get(0);
            authorName = p.name != null ? p.name : authorName;
            birth = p.birth_year;
            death = p.death_year;
        }

        Author author = authorRepo.findByNameIgnoreCase(authorName)
                .orElseGet(() -> authorRepo.save(new Author(authorName, birth, death)));

        // Avoid duplicates by title (case-insensitive)
        if (bookRepo.findByTitleIgnoreCase(br.title).isPresent()) {
            return bookRepo.findByTitleIgnoreCase(br.title);
        }

        Book b = new Book(br.title, language, downloads);
        b.setAuthor(author);
        author.addBook(b);
        return Optional.of(bookRepo.save(b));
    }

    public List<Book> listBooks() {
        return bookRepo.findAll();
    }

    public List<Author> listAuthors() {
        return authorRepo.findAll();
    }

    public List<Author> authorsAliveIn(int year) {
        return authorRepo.findAliveInYear(year);
    }

    public List<Book> booksByLanguage(String langCode) {
        return bookRepo.findByLanguageIgnoreCase(langCode.toLowerCase(Locale.ROOT));
    }

    public List<Book> top10ByDownloads() {
        return bookRepo.findTop10ByOrderByDownloadCountDesc();
    }
}
