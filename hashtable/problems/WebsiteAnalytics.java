package com.hashtable.problems;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Problem 5: Real-Time Website Analytics
 * 
 * Process millions of page view events in real time.
 * 
 * Time Complexity:
 * - processEvent: O(1)
 * - getTopPages: O(n log k) where n is pages, k is top count
 * - getUniqueVisitors: O(1)
 * - getTrafficSources: O(1)
 */
public class WebsiteAnalytics {
    private HashMap<String, Integer> pageViews;           // url -> view count
    private HashMap<String, Set<String>> uniqueVisitors;  // url -> set of user IDs (unique visitors)
    private HashMap<String, Integer> trafficSources;      // source -> count
    private long totalEvents = 0;
    
    public WebsiteAnalytics() {
        this.pageViews = new HashMap<>();
        this.uniqueVisitors = new HashMap<>();
        this.trafficSources = new HashMap<>();
    }
    
    /**
     * Process a page view event
     * Time Complexity: O(1)
     */
    public void processEvent(String url, String userId, String source) {
        // Update page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);
        
        // Track unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);
        
        // Track traffic source
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
        
        totalEvents++;
    }
    
    /**
     * Get top K pages by view count
     * Time Complexity: O(n log k) where n is number of pages, k is top count
     */
    public List<String> getTopPages(int k) {
        return pageViews.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(k)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    
    /**
     * Get unique visitors count for a specific URL
     * Time Complexity: O(1)
     */
    public int getUniqueVisitors(String url) {
        return uniqueVisitors.getOrDefault(url, new HashSet<>()).size();
    }
    
    /**
     * Get all unique visitors for a URL
     * Time Complexity: O(1)
     */
    public Set<String> getVisitors(String url) {
        return uniqueVisitors.getOrDefault(url, new HashSet<>());
    }
    
    /**
     * Get traffic sources ranking
     * Time Complexity: O(n log n) where n is number of sources
     */
    public List<Map.Entry<String, Integer>> getTrafficSources() {
        return trafficSources.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .collect(Collectors.toList());
    }
    
    /**
     * Get page view count
     * Time Complexity: O(1)
     */
    public int getPageViews(String url) {
        return pageViews.getOrDefault(url, 0);
    }
    
    /**
     * Get bounce rate for a page (simplified - return 0 for now)
     * Time Complexity: O(1)
     */
    public double getEngagementRate(String url) {
        // Simplified: return ratio of unique visitors to total views
        if (!pageViews.containsKey(url)) {
            return 0.0;
        }
        return (double) getUniqueVisitors(url) / pageViews.get(url) * 100;
    }
    
    /**
     * Get analytics summary
     * Time Complexity: O(1)
     */
    public String getAnalyticsSummary() {
        return String.format(
            "Total Events: %d, Unique Pages: %d, Unique Sources: %d",
            totalEvents, pageViews.size(), trafficSources.size()
        );
    }
    
    /**
     * Get total page views across all pages
     * Time Complexity: O(1)
     */
    public long getTotalPageViews() {
        return totalEvents;
    }
}
