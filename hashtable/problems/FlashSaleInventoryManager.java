package com.hashtable.problems;

import java.util.*;

/**
 * Problem 2: Flash Sale Inventory Manager
 * 
 * Manage limited stock during flash sales with thousands of concurrent purchases.
 * 
 * Time Complexity:
 * - checkStock: O(1)
 * - purchaseItem: O(1)
 * - getWaitingList: O(1)
 * - getAllStock: O(n)
 */
public class FlashSaleInventoryManager {
    private HashMap<String, Integer> productStock;              // productId -> stock count
    private HashMap<String, Queue<Integer>> waitingList;        // productId -> queue of userIds
    private HashMap<String, Integer> purchaseCount;             // track purchases per product
    
    public FlashSaleInventoryManager() {
        this.productStock = new HashMap<>();
        this.waitingList = new HashMap<>();
        this.purchaseCount = new HashMap<>();
    }
    
    /**
     * Add product with initial stock
     * Time Complexity: O(1)
     */
    public void addProduct(String productId, int initialStock) {
        productStock.put(productId, initialStock);
        waitingList.put(productId, new LinkedList<>());
        purchaseCount.put(productId, 0);
    }
    
    /**
     * Check current stock for a product
     * Time Complexity: O(1)
     */
    public int checkStock(String productId) {
        return productStock.getOrDefault(productId, 0);
    }
    
    /**
     * Purchase item if in stock, otherwise add to waiting list
     * Time Complexity: O(1)
     */
    public boolean purchaseItem(String productId, int userId) {
        if (!productStock.containsKey(productId)) {
            return false;
        }
        
        int currentStock = productStock.get(productId);
        
        if (currentStock > 0) {
            // Stock available - complete purchase
            productStock.put(productId, currentStock - 1);
            purchaseCount.put(productId, purchaseCount.get(productId) + 1);
            return true;
        } else {
            // Out of stock - add to waiting list
            waitingList.get(productId).offer(userId);
            return false;
        }
    }
    
    /**
     * Restock product and process waiting list
     * Time Complexity: O(k) where k is number of items restocked
     */
    public List<Integer> restockItem(String productId, int quantity) {
        List<Integer> processedUsers = new ArrayList<>();
        
        if (!productStock.containsKey(productId)) {
            return processedUsers;
        }
        
        int restockQuantity = quantity;
        Queue<Integer> queue = waitingList.get(productId);
        
        // Process waiting list
        while (restockQuantity > 0 && !queue.isEmpty()) {
            int userId = queue.poll();
            processedUsers.add(userId);
            restockQuantity--;
        }
        
        // Add remaining stock
        int currentStock = productStock.get(productId);
        productStock.put(productId, currentStock + restockQuantity);
        
        return processedUsers;
    }
    
    /**
     * Get waiting list for a product
     * Time Complexity: O(1)
     */
    public Queue<Integer> getWaitingList(String productId) {
        return waitingList.getOrDefault(productId, new LinkedList<>());
    }
    
    /**
     * Get waiting list size
     * Time Complexity: O(1)
     */
    public int getWaitingListSize(String productId) {
        return waitingList.getOrDefault(productId, new LinkedList<>()).size();
    }
    
    /**
     * Get purchase statistics
     * Time Complexity: O(1)
     */
    public int getPurchaseCount(String productId) {
        return purchaseCount.getOrDefault(productId, 0);
    }
    
    /**
     * Check if product exists
     * Time Complexity: O(1)
     */
    public boolean productExists(String productId) {
        return productStock.containsKey(productId);
    }
}
