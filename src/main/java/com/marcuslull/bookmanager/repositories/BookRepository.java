package com.marcuslull.bookmanager.repositories;

import com.marcuslull.bookmanager.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    boolean existsByDedupeId(String dedupeId);

    Page<BookEntity> findAll(Pageable pageable);
}
