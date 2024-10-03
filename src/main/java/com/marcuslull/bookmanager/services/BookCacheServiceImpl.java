package com.marcuslull.bookmanager.services;

import com.marcuslull.bookmanager.entities.BookEntity;
import com.marcuslull.bookmanager.repositories.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class BookCacheServiceImpl implements BookCacheService {

    private final BookRepository bookRepository;

    public BookCacheServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "books", key = "#id", sync = true)
    @Override
    public BookEntity findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @CachePut(value = "books", key = "#bookEntity.getId()")
    @Override
    public BookEntity putBook(BookEntity bookEntity) {
        return bookEntity;
    }

    @CacheEvict(value = "books", key = "#id")
    @Override
    public void cacheEvictBook(Long id) {}
}
