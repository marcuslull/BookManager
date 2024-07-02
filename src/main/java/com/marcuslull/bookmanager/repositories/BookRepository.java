package com.marcuslull.bookmanager.repositories;

import com.marcuslull.bookmanager.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
