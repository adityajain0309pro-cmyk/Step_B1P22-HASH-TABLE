package com.hashtable.problems;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Problem 4: Plagiarism Detection System
 * 
 * Detect similarity between documents using n-gram hashing.
 * 
 * Time Complexity:
 * - extractNGrams: O(m) where m is number of words
 * - indexDocument: O(m)
 * - findSimilarDocuments: O(m + k*n) where k is number of matching documents
 */
public class PlagiarismDetectionSystem {
    private HashMap<String, Set<String>> ngramToDocuments;  // n-gram -> set of document IDs
    private HashMap<String, Set<String>> documentNGrams;    // documentId -> set of n-grams
    private static final int NGRAM_SIZE = 5;  // 5-word n-grams
    
    public PlagiarismDetectionSystem() {
        this.ngramToDocuments = new HashMap<>();
        this.documentNGrams = new HashMap<>();
    }
    
    /**
     * Extract 5-word n-grams from document
     * Time Complexity: O(m) where m is number of words
     */
    public Set<String> extractNGrams(String document) {
        Set<String> ngrams = new HashSet<>();
        
        // Tokenize document
        String[] words = document.toLowerCase()
            .replaceAll("[^a-z0-9\\s]", "")
            .split("\\s+");
        
        // Extract n-grams
        for (int i = 0; i <= words.length - NGRAM_SIZE; i++) {
            StringBuilder ngram = new StringBuilder();
            for (int j = i; j < i + NGRAM_SIZE; j++) {
                if (j > i) ngram.append(" ");
                ngram.append(words[j]);
            }
            ngrams.add(ngram.toString());
        }
        
        return ngrams;
    }
    
    /**
     * Index a document by extracting and storing its n-grams
     * Time Complexity: O(m) where m is number of words
     */
    public void indexDocument(String documentId, String text) {
        Set<String> ngrams = extractNGrams(text);
        documentNGrams.put(documentId, ngrams);
        
        // Index each n-gram
        for (String ngram : ngrams) {
            ngramToDocuments.putIfAbsent(ngram, new HashSet<>());
            ngramToDocuments.get(ngram).add(documentId);
        }
    }
    
    /**
     * Find similar documents based on n-gram overlap
     * Time Complexity: O(m + k*n) where m = words in text, k = matching documents, n = average n-grams
     */
    public List<String> findSimilarDocuments(String text) {
        Set<String> textNgrams = extractNGrams(text);
        HashMap<String, Integer> documentSimilarity = new HashMap<>();
        
        // Count matching n-grams for each document
        for (String ngram : textNgrams) {
            if (ngramToDocuments.containsKey(ngram)) {
                for (String docId : ngramToDocuments.get(ngram)) {
                    documentSimilarity.put(docId, documentSimilarity.getOrDefault(docId, 0) + 1);
                }
            }
        }
        
        // Sort by similarity score and return
        return documentSimilarity.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    
    /**
     * Calculate similarity percentage between two documents
     * Time Complexity: O(n) where n is average number of n-grams
     */
    public double calculateSimilarityPercentage(String documentId, String text) {
        Set<String> textNgrams = extractNGrams(text);
        Set<String> docNgrams = documentNGrams.getOrDefault(documentId, new HashSet<>());
        
        if (textNgrams.isEmpty() && docNgrams.isEmpty()) {
            return 100.0;
        }
        
        if (textNgrams.isEmpty() || docNgrams.isEmpty()) {
            return 0.0;
        }
        
        // Calculate Jaccard similarity: |A ∩ B| / |A ∪ B|
        Set<String> intersection = new HashSet<>(textNgrams);
        intersection.retainAll(docNgrams);
        
        Set<String> union = new HashSet<>(textNgrams);
        union.addAll(docNgrams);
        
        return (double) intersection.size() / union.size() * 100;
    }
    
    /**
     * Get total documents indexed
     * Time Complexity: O(1)
     */
    public int getTotalDocuments() {
        return documentNGrams.size();
    }
    
    /**
     * Get total unique n-grams
     * Time Complexity: O(1)
     */
    public int getTotalNGrams() {
        return ngramToDocuments.size();
    }
}
