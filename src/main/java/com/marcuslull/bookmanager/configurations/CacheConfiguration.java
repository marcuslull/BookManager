package com.marcuslull.bookmanager.configurations;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration  implements CachingConfigurer {

    @Bean
    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("caffeine");
        cacheManager.registerCustomCache("books", Caffeine.newBuilder()
                        .expireAfterAccess(Duration.ofMinutes(10))
                        .buildAsync());
        return cacheManager;
    }
}
