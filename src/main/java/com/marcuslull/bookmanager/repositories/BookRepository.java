package com.marcuslull.bookmanager.repositories;

import com.marcuslull.bookmanager.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * BookRepository is an interface for performing CRUD operations on the BookEntity.
 * It extends the CrudRepository interface provided by Spring Data JPA, adding custom methods
 * specific to book entities.
 *
 * <p><b>Custom Methods:</b></p>
 * <ul>
 *   <li>{@link #existsByDedupeId(String)}: Checks whether a book entity exists with the given deduplication ID.</li>
 *   <li>{@link #findAll(Pageable)}: Retrieves a paginated list of book entities.</li>
 * </ul>
 *
 * <p>Book entities are represented by the {@link BookEntity} class, and the primary key type is Long.</p>
 */
public interface BookRepository extends CrudRepository<BookEntity, Long> {

    boolean existsByDedupeId(String dedupeId);

    Page<BookEntity> findAll(Pageable pageable);
}
