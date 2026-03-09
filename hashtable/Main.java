package com.hashtable;

import com.hashtable.problems.*;
import java.util.*;

/**
 * Main class demonstrating all 10 Hash Table System Design Problems
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("HASH TABLE SYSTEM DESIGN PROBLEMS - DEMONSTRATION");
        System.out.println("=".repeat(80));
        
        demo1_UsernameAvailability();
        demo2_FlashSaleInventory();
        demo3_DNSCache();
        demo4_PlagiarismDetection();
        demo5_WebsiteAnalytics();
        demo6_RateLimiter();
        demo7_Autocomplete();
        demo8_ParkingLot();
        demo9_TransactionAnalyzer();
        demo10_MultiLevelCache();
    }
    
    /**
     * Problem 1: Username Availability Checker
     */
    public static void demo1_UsernameAvailability() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 1: Username Availability Checker");
        System.out.println("-".repeat(80));
        
        UsernameAvailabilityChecker checker = new UsernameAvailabilityChecker();
        
        // Register users
        System.out.println("\nRegistering users:");
        System.out.println("john_doe: " + checker.registerUser("john_doe", 1));
        System.out.println("jane_smith: " + checker.registerUser("jane_smith", 2));
        System.out.println("john_doe (duplicate): " + checker.registerUser("john_doe", 3));
        
        // Check availability
        System.out.println("\nChecking availability:");
        System.out.println("john_doe available: " + checker.checkAvailability("john_doe"));
        System.out.println("available_user available: " + checker.checkAvailability("available_user"));
        
        // Track attempts
        System.out.println("\nTracking attempts:");
        checker.trackAttempt("john_doe");
        checker.trackAttempt("john_doe");
        checker.trackAttempt("jane_smith");
        checker.trackAttempt("test_user");
        checker.trackAttempt("test_user");
        checker.trackAttempt("test_user");
        System.out.println("Most attempted: " + checker.getMostAttemptedUsername());
        
        // Suggest alternatives
        System.out.println("\nAlternative suggestions:");
        System.out.println("john_doe alternatives: " + checker.suggestAlternatives("john_doe"));
    }
    
    /**
     * Problem 2: Flash Sale Inventory Manager
     */
    public static void demo2_FlashSaleInventory() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 2: Flash Sale Inventory Manager");
        System.out.println("-".repeat(80));
        
        FlashSaleInventoryManager inventory = new FlashSaleInventoryManager();
        
        // Add products
        System.out.println("\nAdding products:");
        inventory.addProduct("LAPTOP_PRO", 2);
        inventory.addProduct("PHONE_X", 3);
        System.out.println("LAPTOP_PRO stock: " + inventory.checkStock("LAPTOP_PRO"));
        System.out.println("PHONE_X stock: " + inventory.checkStock("PHONE_X"));
        
        // Simulate purchases
        System.out.println("\nSimulating purchases:");
        System.out.println("User 101 buys LAPTOP_PRO: " + inventory.purchaseItem("LAPTOP_PRO", 101));
        System.out.println("User 102 buys LAPTOP_PRO: " + inventory.purchaseItem("LAPTOP_PRO", 102));
        System.out.println("User 103 buys LAPTOP_PRO: " + inventory.purchaseItem("LAPTOP_PRO", 103) + " (out of stock)");
        System.out.println("User 104 buys PHONE_X: " + inventory.purchaseItem("PHONE_X", 104));
        
        // Check waiting list
        System.out.println("\nWaiting list for LAPTOP_PRO: " + inventory.getWaitingList("LAPTOP_PRO"));
        System.out.println("Purchases for LAPTOP_PRO: " + inventory.getPurchaseCount("LAPTOP_PRO"));
        
        // Restock
        System.out.println("\nRestocking 2 units of LAPTOP_PRO for users:");
        List<Integer> processed = inventory.restockItem("LAPTOP_PRO", 2);
        System.out.println("Processed users: " + processed);
    }
    
    /**
     * Problem 3: DNS Cache with TTL
     */
    public static void demo3_DNSCache() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 3: DNS Cache with TTL");
        System.out.println("-".repeat(80));
        
        DNSCacheSystem dnsCache = new DNSCacheSystem();
        
        // Add entries
        System.out.println("\nAdding DNS entries:");
        dnsCache.addEntry("google.com", "142.251.41.14", 3600);
        dnsCache.addEntry("github.com", "140.82.113.4", 1800);
        dnsCache.addEntry("stackoverflow.com", "151.101.1.69", 900);
        System.out.println("Added 3 DNS entries with different TTLs");
        
        // Resolve queries
        System.out.println("\nResolving queries:");
        System.out.println("google.com -> " + dnsCache.resolve("google.com"));
        System.out.println("github.com -> " + dnsCache.resolve("github.com"));
        System.out.println("unknown.com -> " + dnsCache.resolve("unknown.com"));
        System.out.println("google.com -> " + dnsCache.resolve("google.com") + " (hit)");
        
        // Cache stats
        System.out.println("\n" + dnsCache.getCacheStats());
        System.out.println("Cache Size: " + dnsCache.getCacheSize());
    }
    
    /**
     * Problem 4: Plagiarism Detection System
     */
    public static void demo4_PlagiarismDetection() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 4: Plagiarism Detection System");
        System.out.println("-".repeat(80));
        
        PlagiarismDetectionSystem plagiarism = new PlagiarismDetectionSystem();
        
        // Index documents
        System.out.println("\nIndexing documents:");
        String doc1 = "the quick brown fox jumps over the lazy dog and runs fast";
        String doc2 = "the quick brown fox jumps over the lazy cat and runs slow";
        String doc3 = "artificial intelligence is transforming the world today";
        
        plagiarism.indexDocument("doc1", doc1);
        plagiarism.indexDocument("doc2", doc2);
        plagiarism.indexDocument("doc3", doc3);
        System.out.println("Indexed 3 documents");
        
        // Find similar documents
        System.out.println("\nFinding similar documents to doc1:");
        List<String> similar = plagiarism.findSimilarDocuments(doc1);
        System.out.println("Similar documents: " + similar);
        
        // Calculate similarity
        System.out.println("\nSimilarity percentages:");
        System.out.println("doc1 vs doc2: " + String.format("%.2f%%", plagiarism.calculateSimilarityPercentage("doc2", doc1)));
        System.out.println("doc1 vs doc3: " + String.format("%.2f%%", plagiarism.calculateSimilarityPercentage("doc3", doc1)));
        
        System.out.println("\nStats: Documents=" + plagiarism.getTotalDocuments() + 
                         ", N-grams=" + plagiarism.getTotalNGrams());
    }
    
    /**
     * Problem 5: Real-Time Website Analytics
     */
    public static void demo5_WebsiteAnalytics() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 5: Real-Time Website Analytics");
        System.out.println("-".repeat(80));
        
        WebsiteAnalytics analytics = new WebsiteAnalytics();
        
        // Process events
        System.out.println("\nProcessing page view events:");
        analytics.processEvent("/home", "user1", "google");
        analytics.processEvent("/home", "user2", "google");
        analytics.processEvent("/products", "user1", "direct");
        analytics.processEvent("/products", "user3", "facebook");
        analytics.processEvent("/about", "user2", "twitter");
        analytics.processEvent("/home", "user4", "google");
        System.out.println("Processed 6 events");
        
        // Get top pages
        System.out.println("\nTop 3 pages:");
        List<String> topPages = analytics.getTopPages(3);
        for (String page : topPages) {
            System.out.println("  " + page + ": " + analytics.getPageViews(page) + " views");
        }
        
        // Traffic sources
        System.out.println("\nTraffic sources:");
        List<Map.Entry<String, Integer>> sources = analytics.getTrafficSources();
        for (Map.Entry<String, Integer> source : sources) {
            System.out.println("  " + source.getKey() + ": " + source.getValue());
        }
        
        // Unique visitors
        System.out.println("\nUnique visitors:");
        System.out.println("  /home: " + analytics.getUniqueVisitors("/home"));
        System.out.println("  /products: " + analytics.getUniqueVisitors("/products"));
        System.out.println("  /about: " + analytics.getUniqueVisitors("/about"));
    }
    
    /**
     * Problem 6: Distributed Rate Limiter
     */
    public static void demo6_RateLimiter() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 6: Distributed Rate Limiter (Token Bucket)");
        System.out.println("-".repeat(80));
        
        DistributedRateLimiter limiter = new DistributedRateLimiter();
        
        System.out.println("\nMaking requests:");
        
        // Client 1
        System.out.println("\nClient 1:");
        boolean[] results = new boolean[10];
        for (int i = 0; i < 10; i++) {
            results[i] = limiter.allowRequest("client1");
        }
        System.out.println("Requests 1-10: " + Arrays.toString(results));
        System.out.println(limiter.getRateLimitStatus("client1"));
        
        // Client 2
        System.out.println("\nClient 2:");
        boolean[] results2 = new boolean[10];
        for (int i = 0; i < 10; i++) {
            results2[i] = limiter.allowRequest("client2");
        }
        System.out.println("Requests 1-10: " + Arrays.toString(results2));
        System.out.println(limiter.getRateLimitStatus("client2"));
        
        System.out.println("\nActive clients: " + limiter.getActiveClientCount());
    }
    
    /**
     * Problem 7: Autocomplete System
     */
    public static void demo7_Autocomplete() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 7: Autocomplete System");
        System.out.println("-".repeat(80));
        
        AutocompleteSystem autocomplete = new AutocompleteSystem();
        
        // Add queries
        System.out.println("\nAdding search queries:");
        String[] queries = {"java tutorial", "javascript", "java", "java spring", "java spring boot", 
                           "javascript react", "java", "java", "javascript"};
        for (String query : queries) {
            autocomplete.addQuery(query);
        }
        System.out.println("Added 9 queries (with duplicates)");
        
        // Search with prefix
        System.out.println("\nSearch results:");
        System.out.println("Prefix 'java': " + autocomplete.search("java"));
        System.out.println("Prefix 'java s': " + autocomplete.search("java s"));
        System.out.println("Prefix 'javascript': " + autocomplete.search("javascript"));
        
        System.out.println("\nQuery frequencies:");
        Set<String> allQueries = autocomplete.getAllQueries();
        for (String query : allQueries) {
            System.out.println("  " + query + ": " + autocomplete.getFrequency(query));
        }
    }
    
    /**
     * Problem 8: Smart Parking Lot
     */
    public static void demo8_ParkingLot() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 8: Smart Parking Lot (Open Addressing)");
        System.out.println("-".repeat(80));
        
        SmartParkingLot parkingLot = new SmartParkingLot();
        
        System.out.println("\nParking vehicles:");
        int[] spots = new int[5];
        String[] plates = {"ABC123", "XYZ789", "PQR456", "LMN999", "DEF222"};
        
        for (int i = 0; i < plates.length; i++) {
            spots[i] = parkingLot.parkVehicle(plates[i]);
            System.out.println(plates[i] + " -> Spot #" + spots[i]);
        }
        
        System.out.println("\n" + parkingLot.getStatistics());
        
        // Exit vehicles
        System.out.println("\nVehicles exiting:");
        long duration1 = parkingLot.exitVehicle(plates[0]);
        System.out.println(plates[0] + " parked for: " + duration1 + " seconds");
        
        long duration2 = parkingLot.exitVehicle(plates[1]);
        System.out.println(plates[1] + " parked for: " + duration2 + " seconds");
        
        System.out.println("\n" + parkingLot.getStatistics());
    }
    
    /**
     * Problem 9: Financial Transaction Analyzer
     */
    public static void demo9_TransactionAnalyzer() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 9: Financial Transaction Analyzer");
        System.out.println("-".repeat(80));
        
        FinancialTransactionAnalyzer analyzer = new FinancialTransactionAnalyzer();
        
        // Add transactions
        System.out.println("\nAdding transactions:");
        int[] amounts = {100, 200, 150, 50, 100, 300, 250, 100};
        for (int amount : amounts) {
            analyzer.addTransaction(amount);
        }
        System.out.println("Added 8 transactions: " + Arrays.toString(amounts));
        
        // Detect duplicates
        System.out.println("\nDuplicate amounts: " + analyzer.detectDuplicates());
        System.out.println("Duplicate pairs: " + analyzer.findDuplicatePairs());
        
        // Two-sum
        System.out.println("\nFinding two-sum (target=300):");
        List<Integer[]> pairs = analyzer.findTwoSum(300);
        for (Integer[] pair : pairs) {
            System.out.println("  " + pair[0] + " + " + pair[1] + " = 300");
        }
        
        // Suspicious transactions
        System.out.println("\nSuspicious transactions (> 200):");
        System.out.println(analyzer.findSuspiciousTransactions(200));
        
        // Statistics
        System.out.println("\n" + analyzer.getStatistics());
    }
    
    /**
     * Problem 10: Multi-Level Cache System
     */
    public static void demo10_MultiLevelCache() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PROBLEM 10: Multi-Level Cache System");
        System.out.println("-".repeat(80));
        
        MultiLevelCacheSystem cache = new MultiLevelCacheSystem();
        
        // Add videos
        System.out.println("\nAdding videos to cache:");
        cache.addVideo("video1", "Marvel Movie 1080p");
        cache.addVideo("video2", "Documentary 4K");
        cache.addVideo("video3", "Tutorial Series HD");
        System.out.println("Added 3 videos to L1 cache");
        
        // Access videos
        System.out.println("\nAccessing videos:");
        System.out.println("video1: " + cache.getVideo("video1"));
        System.out.println("video2: " + cache.getVideo("video2"));
        System.out.println("video1: " + cache.getVideo("video1") + " (hit)");
        System.out.println("video3: " + cache.getVideo("video3"));
        System.out.println("video_unknown: " + cache.getVideo("video_unknown") + " (miss)");
        
        // Cache statistics
        System.out.println("\nCache Level Info: " + cache.getLevelStats());
        System.out.println(cache.getStatistics());
        
        // Access counts
        System.out.println("\nAccess counts:");
        System.out.println("video1: " + cache.getAccessCount("video1"));
        System.out.println("video2: " + cache.getAccessCount("video2"));
        System.out.println("video3: " + cache.getAccessCount("video3"));
    }
}
