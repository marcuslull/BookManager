package com.marcuslull.bookmanager.services;

import com.marcuslull.bookmanager.entities.BookEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * Service interface for caching book entities.
 *
 * <p>This interface provides methods for caching and retrieving book entities using their unique identifiers.
 * The intended purpose of this service is to optimize book entity retrieval by reducing database access through caching.
 * </p>
 *
 * <p><b>Methods:</b></p>
 * <ul>
 *   <li>{@link #findBookById(Long)}: Finds and retrieves a book entity by its unique identifier.</li>
 *   <li>{@link #putBook(BookEntity)}: Caches a book entity.</li>
 *   <li>{@link #cacheEvictBook(Long)}: Removes a book entity from the cache.</li>
 * </ul>
 */
public interface BookCacheService {

    /**
     * Finds and retrieves a book entity by its unique identifier.
     *
     * @param id <p>The unique identifier of the book entity to be retrieved.
     *           This identifier must be non-null and of type {@link Long}.</p>
     *
     * @return <p>The {@link BookEntity} corresponding to the provided identifier, or
     *         <code>null</code> if no such entity exists in the cache or database.</p>
     */
    BookEntity findBookById(Long id);

    /**
     * Caches a {@link BookEntity} in the book cache.
     *
     * <p>This method stores the provided book entity in the cache to optimize retrieval and reduce database access.
     * If the book entity already exists in the cache, it will be updated with the new information.
     * The caching mechanism is intended to improve the performance of subsequent lookups for the same book entity.</p>
     *
     * <p><b>Usage:</b></p>
     * <ul>
     *   <li>Store a new book entity in the cache</li>
     *   <li>Update an existing book entity in the cache</li>
     * </ul>
     *
     * @param bookEntity <p>The {@link BookEntity} to be cached. This entity must be non-null and fully populated with valid data.</p>
     *
     * @return <p>The {@link BookEntity} that was stored in the cache. It is the same entity that was passed as an argument.</p>
     */
    BookEntity putBook(BookEntity bookEntity);

    /**
     * Removes a book entity from the cache.
     *
     * <p>This method evicts a book entity from the cache based on its unique identifier.
     * It is useful for maintaining cache consistency, especially after a book entity is deleted or updated in the database.
     * </p>
     *
     * <p><b>Usage:</b></p>
     * <ul>
     *   <li>Invoke this method when a book entity is deleted to ensure it is no longer in the cache.</li>
     *   <li>Invoke this method when a book entity is updated to remove the outdated version from the cache.</li>
     * </ul>
     *
     * @param id <p>The unique identifier of the book entity to be removed from the cache.
     *           This identifier must be non-null and of type {@link Long}.</p>
     */
    void cacheEvictBook(Long id);
}
