package com.hashtable.problems;

import java.util.*;

/**
 * Problem 6: Distributed Rate Limiter
 * 
 * Implement a Token Bucket rate limiter for an API gateway.
 * Limit each client to 1000 requests per hour with burst traffic support.
 * 
 * Time Complexity:
 * - allowRequest: O(1)
 * - refillTokens: O(1)
 * - getRateLimitStatus: O(1)
 */
public class DistributedRateLimiter {
    
    /**
     * Token Bucket implementation
     */
    static class TokenBucket {
        private double tokens;
        private final double capacity;
        private long lastRefillTime;
        private final double refillRate;  // tokens per millisecond
        
        /**
         * Initialize token bucket
         * @param capacity Maximum tokens in bucket
         * @param tokensPerSecond Refill rate
         */
        public TokenBucket(double capacity, double tokensPerSecond) {
            this.capacity = capacity;
            this.tokens = capacity;
            this.refillRate = tokensPerSecond / 1000.0;  // Convert to per millisecond
            this.lastRefillTime = System.currentTimeMillis();
        }
        
        /**
         * Refill tokens based on time elapsed
         * Time Complexity: O(1)
         */
        public void refill() {
            long now = System.currentTimeMillis();
            long timePassed = now - lastRefillTime;
            double tokensToAdd = timePassed * refillRate;
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTime = now;
        }
        
        /**
         * Consume tokens if available
         * Time Complexity: O(1)
         */
        public boolean tryConsume(int tokensNeeded) {
            refill();
            if (tokens >= tokensNeeded) {
                tokens -= tokensNeeded;
                return true;
            }
            return false;
        }
        
        /**
         * Get current token count
         * Time Complexity: O(1)
         */
        public double getTokens() {
            refill();
            return tokens;
        }
    }
    
    private HashMap<String, TokenBucket> clientBuckets;  // clientId -> TokenBucket
    private static final int REQUESTS_PER_HOUR = 1000;
    private static final double TOKENS_PER_SECOND = REQUESTS_PER_HOUR / 3600.0;
    
    public DistributedRateLimiter() {
        this.clientBuckets = new HashMap<>();
    }
    
    /**
     * Check if client can make a request
     * Time Complexity: O(1)
     */
    public boolean allowRequest(String clientId) {
        return allowRequest(clientId, 1);
    }
    
    /**
     * Check if client can make multiple requests
     * Time Complexity: O(1)
     */
    public boolean allowRequest(String clientId, int tokens) {
        // Create bucket if not exists
        clientBuckets.putIfAbsent(clientId, new TokenBucket(REQUESTS_PER_HOUR, TOKENS_PER_SECOND));
        
        TokenBucket bucket = clientBuckets.get(clientId);
        return bucket.tryConsume(tokens);
    }
    
    /**
     * Get rate limit status for a client
     * Time Complexity: O(1)
     */
    public String getRateLimitStatus(String clientId) {
        if (!clientBuckets.containsKey(clientId)) {
            return String.format("Client: %s, Status: No requests made yet", clientId);
        }
        
        TokenBucket bucket = clientBuckets.get(clientId);
        double tokensAvailable = bucket.getTokens();
        double percentageUsed = (REQUESTS_PER_HOUR - tokensAvailable) / REQUESTS_PER_HOUR * 100;
        
        return String.format(
            "Client: %s, Tokens Available: %.2f/%.0f (%.2f%% used)",
            clientId, tokensAvailable, (double) REQUESTS_PER_HOUR, percentageUsed
        );
    }
    
    /**
     * Reset rate limit for a client
     * Time Complexity: O(1)
     */
    public void resetClient(String clientId) {
        clientBuckets.put(clientId, new TokenBucket(REQUESTS_PER_HOUR, TOKENS_PER_SECOND));
    }
    
    /**
     * Get the underlying bucket (for testing)
     * Time Complexity: O(1)
     */
    public TokenBucket getTokenBucket(String clientId) {
        return clientBuckets.get(clientId);
    }
    
    /**
     * Get number of active clients
     * Time Complexity: O(1)
     */
    public int getActiveClientCount() {
        return clientBuckets.size();
    }
}
