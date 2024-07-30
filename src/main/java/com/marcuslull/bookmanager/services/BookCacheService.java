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
    public BookEntity cacheBook(Long id) {
        System.out.println("DB Call");
        return bookRepository.findById(id).orElse(null);
    }

    @CachePut(value = "books", key = "#bookEntity.getId()")
    public BookEntity cachePutBook(BookEntity bookEntity) {
        return bookEntity;
    }

    @CacheEvict(value = "books", key = "#id")
    public Long cacheEvictBook(Long id) {
        return id;
    }
}
