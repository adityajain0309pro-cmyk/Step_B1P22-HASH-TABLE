package com.hashtable.problems;

import java.util.*;

/**
 * Problem 3: DNS Cache with TTL
 * 
 * Implement a DNS caching system that maps domain names to IP addresses with TTL expiration.
 * 
 * Time Complexity:
 * - resolve: O(1)
 * - addEntry: O(1)
 * - removeExpiredEntries: O(n)
 * - getCacheStats: O(1)
 */
public class DNSCacheSystem {
    
    /**
     * DNS Cache Entry with TTL information
     */
    static class DNSEntry {
        String domain;
        String ipAddress;
        long expiryTime;  // System time when entry expires
        
        public DNSEntry(String domain, String ipAddress, long ttlSeconds) {
            this.domain = domain;
            this.ipAddress = ipAddress;
            // TTL is in seconds, convert to system time
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
    
    private LinkedHashMap<String, DNSEntry> cache;  // LRU cache using LinkedHashMap
    private static final int MAX_CACHE_SIZE = 10000;
    private int cacheHits = 0;
    private int cacheMisses = 0;
    
    public DNSCacheSystem() {
        // LinkedHashMap with access-order (LRU behavior)
        this.cache = new LinkedHashMap<String, DNSEntry>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }
    
    /**
     * Resolve domain to IP address (with cache)
     * Time Complexity: O(1)
     */
    public String resolve(String domain) {
        String lowerDomain = domain.toLowerCase();
        
        // Check if in cache
        if (cache.containsKey(lowerDomain)) {
            DNSEntry entry = cache.get(lowerDomain);
            
            // Check if expired
            if (entry.isExpired()) {
                cache.remove(lowerDomain);
                cacheMisses++;
                return null;
            }
            
            cacheHits++;
            return entry.ipAddress;
        }
        
        cacheMisses++;
        return null;
    }
    
    /**
     * Add entry to cache with TTL
     * Time Complexity: O(1)
     */
    public void addEntry(String domain, String ipAddress, int ttlSeconds) {
        String lowerDomain = domain.toLowerCase();
        cache.put(lowerDomain, new DNSEntry(lowerDomain, ipAddress, ttlSeconds));
    }
    
    /**
     * Remove all expired entries
     * Time Complexity: O(n) where n is cache size
     */
    public int removeExpiredEntries() {
        int removedCount = 0;
        List<String> expiredKeys = new ArrayList<>();
        
        for (Map.Entry<String, DNSEntry> entry : cache.entrySet()) {
            if (entry.getValue().isExpired()) {
                expiredKeys.add(entry.getKey());
            }
        }
        
        for (String key : expiredKeys) {
            cache.remove(key);
            removedCount++;
        }
        
        return removedCount;
    }
    
    /**
     * Get cache statistics
     * Time Complexity: O(1)
     */
    public String getCacheStats() {
        int total = cacheHits + cacheMisses;
        double hitRatio = total == 0 ? 0 : (double) cacheHits / total * 100;
        
        return String.format(
            "Cache Stats: Hits=%d, Misses=%d, Size=%d, MaxSize=%d, Hit Ratio=%.2f%%",
            cacheHits, cacheMisses, cache.size(), MAX_CACHE_SIZE, hitRatio
        );
    }
    
    /**
     * Manual cache reset
     * Time Complexity: O(1)
     */
    public void resetStats() {
        cacheHits = 0;
        cacheMisses = 0;
        cache.clear();
    }
    
    /**
     * Get current cache size
     * Time Complexity: O(1)
     */
    public int getCacheSize() {
        return cache.size();
    }
}
