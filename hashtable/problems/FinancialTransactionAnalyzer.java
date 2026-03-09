package com.hashtable.problems;

import java.util.*;

/**
 * Problem 9: Financial Transaction Analyzer
 * 
 * Analyze transactions to detect suspicious patterns using sum-based algorithms.
 * Supports two-sum, duplicate detection, and k-sum problems.
 * 
 * Time Complexity:
 * - findTwoSum: O(n)
 * - detectDuplicates: O(n)
 * - findKSum: O(n^(k-1))
 */
public class FinancialTransactionAnalyzer {
    private List<Integer> transactions;  // List of transaction amounts
    private HashMap<Integer, Integer> transactionFrequency;  // transaction amount -> frequency
    
    public FinancialTransactionAnalyzer() {
        this.transactions = new ArrayList<>();
        this.transactionFrequency = new HashMap<>();
    }
    
    /**
     * Add a transaction
     * Time Complexity: O(1)
     */
    public void addTransaction(int amount) {
        transactions.add(amount);
        transactionFrequency.put(amount, transactionFrequency.getOrDefault(amount, 0) + 1);
    }
    
    /**
     * Find two transactions that sum to target (Two Sum problem)
     * Time Complexity: O(n)
     */
    public List<Integer[]> findTwoSum(int target) {
        List<Integer[]> result = new ArrayList<>();
        HashSet<Integer> seen = new HashSet<>();
        
        for (int transaction : transactions) {
            int complement = target - transaction;
            
            if (seen.contains(complement)) {
                result.add(new Integer[]{complement, transaction});
            }
            seen.add(transaction);
        }
        
        return result;
    }
    
    /**
     * Detect duplicate transactions
     * Time Complexity: O(n)
     */
    public List<Integer> detectDuplicates() {
        List<Integer> duplicates = new ArrayList<>();
        
        for (Map.Entry<Integer, Integer> entry : transactionFrequency.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.add(entry.getKey());
            }
        }
        
        return duplicates;
    }
    
    /**
     * Find all pairs with duplicate amounts
     * Time Complexity: O(n)
     */
    public List<Integer[]> findDuplicatePairs() {
        List<Integer[]> duplicatePairs = new ArrayList<>();
        
        for (Map.Entry<Integer, Integer> entry : transactionFrequency.entrySet()) {
            if (entry.getValue() >= 2) {
                duplicatePairs.add(new Integer[]{entry.getKey(), entry.getValue()});
            }
        }
        
        return duplicatePairs;
    }
    
    /**
     * Find all unique combinations of k transactions that sum to target
     * Time Complexity: O(n^(k-1))
     */
    public List<List<Integer>> findKSum(int k, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        
        // Sort for k-sum algorithm
        List<Integer> sortedTransactions = new ArrayList<>(transactions);
        Collections.sort(sortedTransactions);
        
        kSumHelper(sortedTransactions, k, target, 0, current, result);
        
        return result;
    }
    
    /**
     * Helper method for k-sum using recursion
     * Time Complexity: O(n^(k-1))
     */
    private void kSumHelper(List<Integer> nums, int k, int target, int start,
                           List<Integer> current, List<List<Integer>> result) {
        if (k == 2) {
            // Base case: two-sum with two pointers
            int left = start;
            int right = nums.size() - 1;
            
            while (left < right) {
                int sum = nums.get(left) + nums.get(right);
                if (sum == target) {
                    result.add(new ArrayList<>(current));
                    result.get(result.size() - 1).add(nums.get(left));
                    result.get(result.size() - 1).add(nums.get(right));
                    left++;
                    right--;
                    
                    // Skip duplicates
                    while (left < right && nums.get(left).equals(nums.get(left - 1))) left++;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        } else {
            // Recursive case
            for (int i = start; i < nums.size(); i++) {
                if (i > start && nums.get(i).equals(nums.get(i - 1))) continue;
                
                current.add(nums.get(i));
                kSumHelper(nums, k - 1, target - nums.get(i), i + 1, current, result);
                current.remove(current.size() - 1);
            }
        }
    }
    
    /**
     * Find suspicious transactions (large amounts)
     * Time Complexity: O(n)
     */
    public List<Integer> findSuspiciousTransactions(int threshold) {
        List<Integer> suspicious = new ArrayList<>();
        
        for (int transaction : transactions) {
            if (transaction > threshold) {
                suspicious.add(transaction);
            }
        }
        
        return suspicious;
    }
    
    /**
     * Get transaction statistics
     * Time Complexity: O(1)
     */
    public String getStatistics() {
        if (transactions.isEmpty()) {
            return "No transactions";
        }
        
        int sum = transactions.stream().mapToInt(Integer::intValue).sum();
        double average = (double) sum / transactions.size();
        int max = transactions.stream().mapToInt(Integer::intValue).max().orElse(0);
        int min = transactions.stream().mapToInt(Integer::intValue).min().orElse(0);
        
        return String.format(
            "Total Transactions: %d, Sum: %d, Average: %.2f, Max: %d, Min: %d, Unique Values: %d",
            transactions.size(), sum, average, max, min, transactionFrequency.size()
        );
    }
    
    /**
     * Get total transactions
     * Time Complexity: O(1)
     */
    public int getTotalTransactions() {
        return transactions.size();
    }
}
