package com.marcuslull.bookmanager.repositories;

import com.marcuslull.bookmanager.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    boolean existsByDedupeId(String dedupeId);
}
