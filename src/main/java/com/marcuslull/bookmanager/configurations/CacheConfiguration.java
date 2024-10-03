package com.marcuslull.bookmanager.configurations;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration class for setting up caching in the application.
 * <p>
 * This class enables caching and provides a custom configuration
 * for the cache manager using the Caffeine caching library.
 * </p>
 * <p>
 * Key configurations:
 * <ul>
 *   <li>Cache Name: books</li>
 *   <li>Cache Expiry Time: 10 minutes</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableCaching
public class CacheConfiguration implements CachingConfigurer {
    private final String CACHE_NAME = "books";
    private final int EXPIRY_TIME_IN_MINUTES = 10;

    /**
     * Configures and returns a CacheManager instance using the Caffeine caching library.
     * <p>
     * The CacheManager is customized to use a cache named "books" with a specific expiration
     * policy defined by the configuration settings.
     * </p>
     * <p>
     * Key configurations:
     * <ul>
     *   <li>Cache Name: books</li>
     *   <li>Cache Expiry Time: 10 minutes</li>
     * </ul>
     * </p>
     *
     * @return a CaffeineCacheManager instance configured with a custom cache named "books" and a specific expiration policy.
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("caffeine");
        cacheManager.registerCustomCache(CACHE_NAME, Caffeine.newBuilder()
                        .expireAfterAccess(Duration.ofMinutes(EXPIRY_TIME_IN_MINUTES))
                        .buildAsync());
        return cacheManager;
    }
}
