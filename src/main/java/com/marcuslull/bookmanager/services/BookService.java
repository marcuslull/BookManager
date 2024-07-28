package com.marcuslull.bookmanager.services;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import com.marcuslull.bookmanager.repositories.BookRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final Cache cache;

    public BookService(BookRepository bookRepository, CacheManager cacheManager) {
        this.bookRepository = bookRepository;
        this.cache = cacheManager.getCache("books");
    }

    @Cacheable(value = "books", key = "#id")
    public BookEntity findById(Long id) {
        System.out.println("DB retrieval");
        return bookRepository.findById(id).orElse(null);
    }

    public Iterable<BookEntity> findAll(){
        // future operations
        return bookRepository.findAll();
    }

    public Iterable<BookEntity> saveAll(List<BookDto> bookDtos) {
        List<BookEntity> bookEntities = bookDtos.stream().map(BookEntity::fromDto).toList();
        return cachePut(bookRepository.saveAll(bookEntities));
    }

    private Iterable<BookEntity> cachePut(Iterable<BookEntity> books) {
        assert cache != null;
        books.forEach(bookEntity -> cache.putIfAbsent(bookEntity.getId(), bookEntity));
        return books;
    }
}
