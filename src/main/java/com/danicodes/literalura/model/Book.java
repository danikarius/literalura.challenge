package com.danicodes.literalura.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 5)
    private String language;

    private Integer downloadCount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Author author;

    public Book() {}

    public Book(String title, String language, Integer downloadCount) {
        this.title = title;
        this.language = language;
        this.downloadCount = downloadCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
               Objects.equals(author != null ? author.getName() : null, book.author != null ? book.author.getName() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Livro: " + title + " | Idioma: " + language + " | Downloads: " + downloadCount +
                (author != null ? " | Autor: " + author.getName() : "");
    }
}
