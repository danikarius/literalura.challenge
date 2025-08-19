package com.danicodes.literalura.repository;

import com.danicodes.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLanguageIgnoreCase(String language);
    Optional<Book> findByTitleIgnoreCase(String title);
    List<Book> findTop10ByOrderByDownloadCountDesc();
}
