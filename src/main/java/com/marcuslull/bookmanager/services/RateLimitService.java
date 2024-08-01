package com.marcuslull.bookmanager.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private static final Map<String, Instant> rateLimitMap = new ConcurrentHashMap<>();

    public boolean isLimited(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        Instant now = Instant.now();
        if (rateLimitMap.containsKey(ipAddress)) {
            Instant lastRequestTime = rateLimitMap.get(ipAddress);
            if (now.isAfter(lastRequestTime.plusSeconds(5))) {
                rateLimitMap.replace(ipAddress, now);
                return false;
            }
            return true;
        }
        rateLimitMap.put(ipAddress, now);
        return false;
    }
}
