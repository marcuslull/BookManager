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
        String ipAddressSequence = getIpAddressString(request);
        Instant now = Instant.now();
        if (rateLimitMap.containsKey(ipAddressSequence)) {
            Instant lastRequestTime = rateLimitMap.get(ipAddressSequence);
            if (now.isAfter(lastRequestTime.plusSeconds(5))) {
                rateLimitMap.replace(ipAddressSequence, now);
                return false;
            }
            return true;
        }
        rateLimitMap.put(ipAddressSequence, now);
        return false;
    }

    private String getIpAddressString(HttpServletRequest request) {
        String ipAddressSequence = request.getHeader("X-Forwarded-For");
        if (ipAddressSequence == null || ipAddressSequence.isEmpty()) {
            ipAddressSequence = request.getRemoteAddr();
        }
        return ipAddressSequence;
    }
}
