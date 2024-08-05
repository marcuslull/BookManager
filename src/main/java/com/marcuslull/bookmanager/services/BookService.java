package com.marcuslull.bookmanager.services;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.dtos.PageDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import com.marcuslull.bookmanager.exceptions.DefensiveNullException;
import com.marcuslull.bookmanager.exceptions.DuplicateEntityException;
import com.marcuslull.bookmanager.mappers.PageableMapper;
import com.marcuslull.bookmanager.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookCacheService bookCacheService;

    public BookService(BookRepository bookRepository, BookCacheService bookCacheService) {
        this.bookRepository = bookRepository;
        this.bookCacheService = bookCacheService;
    }

    public BookEntity findById(Long id) {
        defensiveNullCheck(List.of(id));
        return bookCacheService.cacheBook(id);
    }

    public PageDto findAllPaged(Pageable pageable){
        defensiveNullCheck(List.of(pageable));
        Page<BookEntity> pageOfBookEntities =  bookRepository.findAll(pageable);
        return PageableMapper.pageableToPageDto(pageOfBookEntities);
    }

    @Transactional
    public Iterable<BookEntity> saveAll(List<BookDto> bookDtos) {
        defensiveNullCheck(List.of(bookDtos));
        List<BookEntity> bookEntities = bookDtos.stream().map(BookEntity::fromDto).toList();
        bookEntities = bookDeduplication(bookEntities);
        if (bookEntities.isEmpty()){
            throw new DuplicateEntityException("Book(s) already exists");
        }
        Iterable<BookEntity> result = bookRepository.saveAll(bookEntities);
        cachePut(result);
        return result;
    }

    @Transactional
    public void deleteById(Long id) {
        defensiveNullCheck(List.of(id));
        bookCacheService.cacheEvictBook(id);
        bookRepository.deleteById(id);
    }

    private void cachePut(Iterable<BookEntity> bookEntities) {
        for(BookEntity bookEntity : bookEntities) {
            bookCacheService.cachePutBook(bookEntity);
        }
    }

    private List<BookEntity> bookDeduplication(List<BookEntity> bookEntities) {
        return bookEntities.stream().filter(book -> !bookRepository.existsByDedupeId(book.getDedupeId())).toList();
    }

    private void defensiveNullCheck(List<Object> objectsList) {
        objectsList.forEach(object -> {
            if (object == null) {
                throw new DefensiveNullException();
            }
        });
    }
}