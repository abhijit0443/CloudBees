package com.assessment.inc.services;

import com.assessment.inc.entites.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    List<Inventory> saveOrUpdateInventory(List<Inventory> inventory);
    Inventory save(Inventory inventory);

    List<Inventory> getAllInventories();

    List<Inventory> getByFromLocationAndToLocation(String fromLocation, String toLocation) ;

    Inventory getInventoryById(String trainId);

    void deleteInventory(String trainId);
}