package com.marcuslull.bookmanager.services;

import com.marcuslull.bookmanager.entities.BookEntity;
import com.marcuslull.bookmanager.repositories.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookCacheService {

    private final BookRepository bookRepository;

    public BookCacheService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "books", key = "#id", sync = true)
    public BookEntity findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @CachePut(value = "books", key = "#bookEntity.getId()")
    public BookEntity putBook(BookEntity bookEntity) {
        return bookEntity;
    }

    @CacheEvict(value = "books", key = "#id")
    public void cacheEvictBook(Long id) {}
}
