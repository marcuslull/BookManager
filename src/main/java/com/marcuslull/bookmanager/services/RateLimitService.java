package com.marcuslull.bookmanager.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private static final Map<String, Instant> rateLimitMap = new ConcurrentHashMap<>();
    private final int RATE_LIMIT_SECONDS = 5;

    public boolean isLimited(HttpServletRequest request) {
        String ipAddressSequence = getIpAddressString(request);
        Instant now = Instant.now();
        // Usage of `.merge()` removes the compound operations otherwise needed.
        // This makes a single atomic operation that eliminates possible race conditions.
        // If `now` is merged/returned, the last successful accessed time for that client is greater than
        // `RATE_LIMIT_SECONDS` seconds ago and therefor not rate limited.
        return rateLimitMap.merge(ipAddressSequence, now, (oldValue, newValue) ->
                now.isAfter(oldValue.plusSeconds(RATE_LIMIT_SECONDS)) ?  newValue : oldValue) != now;
    }

    private String getIpAddressString(HttpServletRequest request) {
        String ipAddressSequence = request.getHeader("X-Forwarded-For");
        if (ipAddressSequence == null || ipAddressSequence.isEmpty()) {
            ipAddressSequence = request.getRemoteAddr();
        }
        return ipAddressSequence;
    }
}
