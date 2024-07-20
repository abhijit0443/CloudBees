package com.assessment.inc.services;

import com.assessment.inc.entites.Inventory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InventoryService {

    List<Inventory> saveOrUpdateInventory(List<Inventory> inventory);
    Inventory save(Inventory inventory);

    List<Inventory> getAllInventories();

    List<Inventory> getByFromLocationAndToLocation(String fromLocation, String toLocation) ;

    List<Inventory> findByTrainId(String trainId);

    void deleteInventory(String trainId);

    Inventory findByTrainIdAndDate(String trainId, LocalDate date);
}