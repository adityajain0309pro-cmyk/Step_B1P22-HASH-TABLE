package com.hashtable.problems;

import java.util.*;

/**
 * Problem 10: Multi-Level Cache System
 * 
 * Simulate a cache system used by video streaming services with LRU eviction.
 * 
 * Cache Levels:
 * L1: In-memory cache (10,000 items)
 * L2: SSD cache (100,000 items)
 * L3: Database
 * 
 * Time Complexity:
 * - getVideo: O(1) average
 * - promoteToL1: O(1) average
 * - getStatistics: O(1)
 */
public class MultiLevelCacheSystem {
    
    /**
     * Video entry with metadata
     */
    static class VideoEntry {
        String videoId;
        String data;
        long lastAccessTime;
        int accessCount;
        
        public VideoEntry(String videoId, String data) {
            this.videoId = videoId;
            this.data = data;
            this.lastAccessTime = System.currentTimeMillis();
            this.accessCount = 1;
        }
    }
    
    private static final int L1_CAPACITY = 10000;
    private static final int L2_CAPACITY = 100000;
    
    // L1: In-memory cache using LinkedHashMap (LRU)
    private LinkedHashMap<String, VideoEntry> l1Cache;
    
    // L2: SSD cache
    private LinkedHashMap<String, VideoEntry> l2Cache;
    
    // L3: Database (simulated with HashMap)
    private HashMap<String, VideoEntry> l3Cache;
    
    // Statistics
    private long l1Hits = 0;
    private long l2Hits = 0;
    private long l3Hits = 0;
    private long misses = 0;
    
    public MultiLevelCacheSystem() {
        // L1 with LRU eviction
        this.l1Cache = new LinkedHashMap<String, VideoEntry>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, VideoEntry> eldest) {
                if (size() > L1_CAPACITY) {
                    // Demote to L2 before eviction
                    l2Cache.put(eldest.getKey(), eldest.getValue());
                    return true;
                }
                return false;
            }
        };
        
        // L2 with LRU eviction
        this.l2Cache = new LinkedHashMap<String, VideoEntry>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, VideoEntry> eldest) {
                if (size() > L2_CAPACITY) {
                    // Demote to L3 before eviction
                    l3Cache.put(eldest.getKey(), eldest.getValue());
                    return true;
                }
                return false;
            }
        };
        
        this.l3Cache = new HashMap<>();
    }
    
    /**
     * Get video from cache system (tries all levels)
     * Time Complexity: O(1)
     */
    public String getVideo(String videoId) {
        // Try L1
        if (l1Cache.containsKey(videoId)) {
            VideoEntry entry = l1Cache.get(videoId);
            entry.lastAccessTime = System.currentTimeMillis();
            entry.accessCount++;
            l1Hits++;
            return entry.data;
        }
        
        // Try L2
        if (l2Cache.containsKey(videoId)) {
            VideoEntry entry = l2Cache.get(videoId);
            entry.lastAccessTime = System.currentTimeMillis();
            entry.accessCount++;
            l2Hits++;
            
            // Promote to L1
            promoteToL1(videoId);
            return entry.data;
        }
        
        // Try L3
        if (l3Cache.containsKey(videoId)) {
            VideoEntry entry = l3Cache.get(videoId);
            entry.lastAccessTime = System.currentTimeMillis();
            entry.accessCount++;
            l3Hits++;
            
            // Promote to L1
            promoteToL1(videoId);
            return entry.data;
        }
        
        misses++;
        return null;
    }
    
    /**
     * Add video to cache (starts at L1)
     * Time Complexity: O(1)
     */
    public void addVideo(String videoId, String data) {
        VideoEntry entry = new VideoEntry(videoId, data);
        l1Cache.put(videoId, entry);
    }
    
    /**
     * Promote video from L2/L3 to L1
     * Time Complexity: O(1)
     */
    public void promoteToL1(String videoId) {
        VideoEntry entry = null;
        
        // Find in L2 or L3
        if (l2Cache.containsKey(videoId)) {
            entry = l2Cache.remove(videoId);
        } else if (l3Cache.containsKey(videoId)) {
            entry = l3Cache.remove(videoId);
        } else {
            return;
        }
        
        // Add to L1
        if (entry != null) {
            l1Cache.put(videoId, entry);
        }
    }
    
    /**
     * Get cache statistics
     * Time Complexity: O(1)
     */
    public String getStatistics() {
        long totalRequests = l1Hits + l2Hits + l3Hits + misses;
        double hitRate = totalRequests == 0 ? 0 : (double) (l1Hits + l2Hits + l3Hits) / totalRequests * 100;
        double l1HitRate = totalRequests == 0 ? 0 : (double) l1Hits / totalRequests * 100;
        
        return String.format(
            "L1 Hits: %d (%.2f%%), L2 Hits: %d, L3 Hits: %d, Misses: %d, Overall Hit Rate: %.2f%%, L1 Size: %d/%d, L2 Size: %d/%d, L3 Size: %d",
            l1Hits, l1HitRate, l2Hits, l3Hits, misses, hitRate,
            l1Cache.size(), L1_CAPACITY, l2Cache.size(), L2_CAPACITY, l3Cache.size()
        );
    }
    
    /**
     * Get cache level statistics
     * Time Complexity: O(1)
     */
    public String getLevelStats() {
        return String.format(
            "L1 Cache: %d items, L2 Cache: %d items, L3 Cache: %d items",
            l1Cache.size(), l2Cache.size(), l3Cache.size()
        );
    }
    
    /**
     * Clear all caches
     * Time Complexity: O(1)
     */
    public void clearAll() {
        l1Cache.clear();
        l2Cache.clear();
        l3Cache.clear();
        l1Hits = 0;
        l2Hits = 0;
        l3Hits = 0;
        misses = 0;
    }
    
    /**
     * Get video access count
     * Time Complexity: O(1)
     */
    public int getAccessCount(String videoId) {
        if (l1Cache.containsKey(videoId)) {
            return l1Cache.get(videoId).accessCount;
        }
        if (l2Cache.containsKey(videoId)) {
            return l2Cache.get(videoId).accessCount;
        }
        if (l3Cache.containsKey(videoId)) {
            return l3Cache.get(videoId).accessCount;
        }
        return 0;
    }
}
