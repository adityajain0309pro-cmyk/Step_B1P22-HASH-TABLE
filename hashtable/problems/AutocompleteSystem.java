package com.hashtable.problems;

import java.util.*;

/**
 * Problem 7: Autocomplete System
 * 
 * Suggest search queries while the user types using a Trie structure
 * and frequency tracking with HashMap.
 * 
 * Time Complexity:
 * - addQuery: O(m) where m is query length
 * - search: O(m + k log k) where m is prefix length, k is results
 * - updateFrequency: O(1)
 */
public class AutocompleteSystem {
    
    /**
     * Trie Node for autocomplete
     */
    static class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<>();
        HashMap<String, Integer> frequency = new HashMap<>();  // query -> frequency
        int maxSize = 10;  // Return top 10 suggestions
    }
    
    private TrieNode root;
    private HashMap<String, Integer> globalFrequency;  // Global query frequency
    
    public AutocompleteSystem() {
        this.root = new TrieNode();
        this.globalFrequency = new HashMap<>();
    }
    
    /**
     * Add a query to the system
     * Time Complexity: O(m) where m is query length
     */
    public void addQuery(String query) {
        String lowerQuery = query.toLowerCase();
        globalFrequency.put(lowerQuery, globalFrequency.getOrDefault(lowerQuery, 0) + 1);
        
        TrieNode node = root;
        for (char c : lowerQuery.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            
            // Update frequency at this node
            node.frequency.put(lowerQuery, node.frequency.getOrDefault(lowerQuery, 0) + 1);
        }
    }
    
    /**
     * Search for queries matching a prefix
     * Time Complexity: O(m + k log k) where m is prefix length, k is result count
     */
    public List<String> search(String prefix) {
        String lowerPrefix = prefix.toLowerCase();
        TrieNode node = root;
        
        // Navigate to prefix node
        for (char c : lowerPrefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return new ArrayList<>();  // No matches
            }
            node = node.children.get(c);
        }
        
        // Collect top suggestions
        return node.frequency.entrySet().stream()
            .sorted((a, b) -> {
                int freqCompare = b.getValue().compareTo(a.getValue());
                if (freqCompare != 0) return freqCompare;
                return a.getKey().compareTo(b.getKey());  // Alphabetical tiebreaker
            })
            .limit(10)
            .map(Map.Entry::getKey)
            .toList();
    }
    
    /**
     * Update frequency of a query
     * Time Complexity: O(1)
     */
    public void updateFrequency(String query) {
        addQuery(query);
    }
    
    /**
     * Get frequency of a query
     * Time Complexity: O(1)
     */
    public int getFrequency(String query) {
        return globalFrequency.getOrDefault(query.toLowerCase(), 0);
    }
    
    /**
     * Get all queries (debugging)
     * Time Complexity: O(n) where n is total queries
     */
    public Set<String> getAllQueries() {
        return new HashSet<>(globalFrequency.keySet());
    }
    
    /**
     * Get total unique queries
     * Time Complexity: O(1)
     */
    public int getTotalUniqueQueries() {
        return globalFrequency.size();
    }
}
