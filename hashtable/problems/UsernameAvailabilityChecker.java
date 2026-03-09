package com.hashtable.problems;

import java.util.*;

/**
 * Problem 1: Username Availability Checker
 * 
 * A social media platform with millions of users needs to check if a username is available instantly.
 * 
 * Time Complexity:
 * - checkAvailability: O(1)
 * - registerUser: O(1)
 * - suggestAlternatives: O(n) where n is number of alternatives
 * - getMostAttemptedUsername: O(1)
 */
public class UsernameAvailabilityChecker {
    private HashMap<String, Integer> usernameToUserId;        // username -> userId
    private HashMap<String, Integer> attemptFrequency;         // username -> attempt count
    
    public UsernameAvailabilityChecker() {
        this.usernameToUserId = new HashMap<>();
        this.attemptFrequency = new HashMap<>();
    }
    
    /**
     * Check if username is available
     * Time Complexity: O(1)
     */
    public boolean checkAvailability(String username) {
        return !usernameToUserId.containsKey(username.toLowerCase());
    }
    
    /**
     * Register a username with userId
     * Time Complexity: O(1)
     */
    public boolean registerUser(String username, int userId) {
        String lowerUsername = username.toLowerCase();
        
        if (usernameToUserId.containsKey(lowerUsername)) {
            return false;
        }
        
        usernameToUserId.put(lowerUsername, userId);
        return true;
    }
    
    /**
     * Track username lookup attempts
     * Time Complexity: O(1)
     */
    public void trackAttempt(String username) {
        String lowerUsername = username.toLowerCase();
        attemptFrequency.put(lowerUsername, attemptFrequency.getOrDefault(lowerUsername, 0) + 1);
    }
    
    /**
     * Get most attempted username
     * Time Complexity: O(1) - returns cached value
     */
    public String getMostAttemptedUsername() {
        return attemptFrequency.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
    
    /**
     * Suggest alternative usernames by appending numbers
     * Time Complexity: O(n) where n is number of alternatives checked
     */
    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        String base = username.toLowerCase();
        
        // Try appending numbers 1-10
        for (int i = 1; i <= 10; i++) {
            String alternative = base + i;
            if (!usernameToUserId.containsKey(alternative)) {
                suggestions.add(alternative);
            }
            if (suggestions.size() == 5) break;  // Return top 5 suggestions
        }
        
        return suggestions;
    }
    
    /**
     * Get user ID for a username
     * Time Complexity: O(1)
     */
    public Integer getUserId(String username) {
        return usernameToUserId.get(username.toLowerCase());
    }
    
    /**
     * Get total registered users
     * Time Complexity: O(1)
     */
    public int getTotalUsers() {
        return usernameToUserId.size();
    }
}
