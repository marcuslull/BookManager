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

/**
 * Service class for managing books, performing CRUD operations, and handling caching.
 *
 * <p>The BookService class is responsible for interacting with the {@link BookRepository} for database operations
 * and the {@link BookCacheService} for caching operations. The service provides methods to find, save, and delete
 * book entities while ensuring data integrity and optimization through deduplication and caching mechanisms.</p>
 *
 * <p><b>Methods:</b></p>
 * <ul>
 *   <li>{@link #findById(Long)}: Finds a book entity by its unique identifier.</li>
 *   <li>{@link #findAllPaged(Pageable)}: Retrieves a paginated list of book entities.</li>
 *   <li>{@link #saveAll(List<BookDto>)}: Saves multiple book entities and ensures deduplication before persisting.</li>
 *   <li>{@link #deleteById(Long)}: Deletes a book entity by its unique identifier.</li>
 * </ul>
 */
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookCacheService bookCacheService;

    public BookService(BookRepository bookRepository, BookCacheService bookCacheService) {
        this.bookRepository = bookRepository;
        this.bookCacheService = bookCacheService;
    }

    /**
     * Finds and retrieves a book entity from the cache or database by its unique identifier.
     *
     * <p>This method will perform a null check on the provided identifier before attempting to retrieve
     * the BookEntity. It utilizes the {@code bookCacheService} to fetch the book entity.</p>
     *
     * @param id <p>The unique identifier of the book entity to be retrieved.
     *           This identifier must be non-null and of type {@link Long}.</p>
     *
     * @return <p>The {@link BookEntity} corresponding to the provided identifier, or
     *         <code>null</code> if no such entity exists in the cache or database.</p>
     */
    public BookEntity findById(Long id) {
        defensiveNullCheck(List.of(id));
        return bookCacheService.findBookById(id);
    }

    /**
     * Retrieves a paginated list of all books.
     * <p>
     * This method performs a defensive null check on the provided {@code pageable} parameter
     * to ensure that it is not null. It then retrieves a page of book entities from the
     * {@code bookRepository} and maps it to a {@code PageDto} object.
     * </p>
     *
     * @param pageable
     * <p>The pagination information, including page number, size, and sorting criteria.</p>
     *
     * @return
     * <p>A {@link PageDto} object representing a page of book entities,
     * including the content and pagination details.</p>
     */
    public PageDto findAllPaged(Pageable pageable){
        defensiveNullCheck(List.of(pageable));
        int pageNumber = pageable.getPageNumber();
        Page<BookEntity> pageOfBookEntities =  bookRepository.findAll(pageable.withPage(pageNumber));
        return PageableMapper.pageableToPageDto(pageOfBookEntities);
    }

    /**
     * Saves a list of {@link BookDto} objects to the database.
     *
     * <p>This method performs the following operations:</p>
     * <ul>
     *   <li>Checks for null values in the provided list.</li>
     *   <li>Converts each {@link BookDto} to a {@link BookEntity}.</li>
     *   <li>Deduplicates the list of {@link BookEntity} objects.</li>
     *   <li>Saves the deduplicated list to the repository.</li>
     *   <li>Caches the saved entities.</li>
     * </ul>
     *
     * @param bookDtos <p>A list of {@link BookDto} objects to be saved. These objects represent the data transfer
     * objects for books and must not be null.</p>
     *
     * @return <p>An {@link Iterable} of {@link BookEntity} objects that were saved to the database, representing the saved
     * entities.</p>
     */
    @Transactional
    public Iterable<BookEntity> saveAll(List<BookDto> bookDtos) {
        defensiveNullCheck(List.of(bookDtos));
        List<BookEntity> bookEntities = bookDtos.stream().map(BookEntity::fromDto).toList();
        bookEntities = bookDeduplication(bookEntities);
        Iterable<BookEntity> result = bookRepository.saveAll(bookEntities);
        cachePut(result);
        return result;
    }

    /**
     * Deletes a book entity by its unique identifier.
     *
     * <p>This method performs the following operations:</p>
     * <ul>
     *   <li>Performs a defensive null check on the provided identifier.</li>
     *   <li>Evicts the book entity from the cache.</li>
     *   <li>Deletes the book entity from the repository.</li>
     * </ul>
     *
     * <p>It uses the {@code defensiveNullCheck} to ensure the identifier is not null.</p>
     *
     * @param id <p>The unique identifier of the book entity to be deleted. This identifier must be non-null and of type {@link Long}.</p>
     */
    @Transactional
    public void deleteById(Long id) {
        defensiveNullCheck(List.of(id));
        bookCacheService.cacheEvictBook(id);
        bookRepository.deleteById(id);
    }

    private void cachePut(Iterable<BookEntity> bookEntities) {
        for(BookEntity bookEntity : bookEntities) {
            bookCacheService.putBook(bookEntity);
        }
    }

    private List<BookEntity> bookDeduplication(List<BookEntity> bookEntities) {
        List<BookEntity> deduplicatedList = bookEntities.stream().filter(book ->
                !bookRepository.existsByDedupeId(book.getDedupeId())).toList();
        if (deduplicatedList.isEmpty()) {
            throw new DuplicateEntityException("Book(s) already exist");
        }
        return deduplicatedList;
    }

    private void defensiveNullCheck(List<Object> objectsList) {
        objectsList.forEach(object -> {
            if (object == null) {
                throw new DefensiveNullException();
            }
        });
    }
}