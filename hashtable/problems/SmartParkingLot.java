package com.hashtable.problems;

import java.util.*;

/**
 * Problem 8: Smart Parking Lot using Open Addressing
 * 
 * Assign parking spots using hash-based allocation with linear probing
 * for collision resolution.
 * 
 * Time Complexity:
 * - parkVehicle: O(1) average
 * - exitVehicle: O(1) average
 * - getStatistics: O(1)
 */
public class SmartParkingLot {
    
    /**
     * Parking spot information
     */
    static class ParkingSpot {
        String licensePlate;
        long entryTime;
        long exitTime;
        boolean isOccupied;
        
        public ParkingSpot(String licensePlate, long entryTime) {
            this.licensePlate = licensePlate;
            this.entryTime = entryTime;
            this.isOccupied = true;
            this.exitTime = -1;
        }
    }
    
    private static final int PARKING_LOT_SIZE = 500;
    private ParkingSpot[] parkingLot;  // Hash table with open addressing
    private HashMap<String, Integer> licensePlateToSpot;  // Quick lookup
    private int occupiedSpots = 0;
    
    public SmartParkingLot() {
        this.parkingLot = new ParkingSpot[PARKING_LOT_SIZE];
        this.licensePlateToSpot = new HashMap<>();
    }
    
    /**
     * Hash function for license plate
     * Time Complexity: O(m) where m is length of license plate
     */
    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % PARKING_LOT_SIZE;
    }
    
    /**
     * Park a vehicle using linear probing
     * Time Complexity: O(1) average, O(n) worst case
     */
    public int parkVehicle(String licensePlate) {
        if (licensePlateToSpot.containsKey(licensePlate)) {
            return -1;  // Vehicle already parked
        }
        
        if (occupiedSpots >= PARKING_LOT_SIZE) {
            return -1;  // Parking lot full
        }
        
        int index = hash(licensePlate);
        int originalIndex = index;
        
        // Linear probing to find empty spot
        while (parkingLot[index] != null && parkingLot[index].isOccupied) {
            index = (index + 1) % PARKING_LOT_SIZE;
            if (index == originalIndex) {
                return -1;  // Lot is full
            }
        }
        
        // Park the vehicle
        parkingLot[index] = new ParkingSpot(licensePlate, System.currentTimeMillis());
        licensePlateToSpot.put(licensePlate, index);
        occupiedSpots++;
        
        return index;
    }
    
    /**
     * Remove vehicle from parking lot
     * Time Complexity: O(1)
     */
    public long exitVehicle(String licensePlate) {
        if (!licensePlateToSpot.containsKey(licensePlate)) {
            return -1;  // Vehicle not found
        }
        
        int spotIndex = licensePlateToSpot.get(licensePlate);
        ParkingSpot spot = parkingLot[spotIndex];
        
        spot.exitTime = System.currentTimeMillis();
        spot.isOccupied = false;
        licensePlateToSpot.remove(licensePlate);
        occupiedSpots--;
        
        // Return parking duration in seconds
        return (spot.exitTime - spot.entryTime) / 1000;
    }
    
    /**
     * Check if spot is available
     * Time Complexity: O(1)
     */
    public boolean isSpotAvailable(int spotNumber) {
        if (spotNumber < 0 || spotNumber >= PARKING_LOT_SIZE) {
            return false;
        }
        return parkingLot[spotNumber] == null || !parkingLot[spotNumber].isOccupied;
    }
    
    /**
     * Get parking statistics
     * Time Complexity: O(1)
     */
    public String getStatistics() {
        double occupancyRate = (double) occupiedSpots / PARKING_LOT_SIZE * 100;
        return String.format(
            "Total Spots: %d, Occupied: %d, Available: %d, Occupancy Rate: %.2f%%",
            PARKING_LOT_SIZE, occupiedSpots, PARKING_LOT_SIZE - occupiedSpots, occupancyRate
        );
    }
    
    /**
     * Get available spots count
     * Time Complexity: O(1)
     */
    public int getAvailableSpots() {
        return PARKING_LOT_SIZE - occupiedSpots;
    }
    
    /**
     * Get vehicle spot number
     * Time Complexity: O(1)
     */
    public Integer getVehicleSpot(String licensePlate) {
        return licensePlateToSpot.get(licensePlate);
    }
}
