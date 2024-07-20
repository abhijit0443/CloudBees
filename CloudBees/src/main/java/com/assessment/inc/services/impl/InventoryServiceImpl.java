package com.assessment.inc.services.impl;

import com.assessment.inc.entites.Inventory;
import com.assessment.inc.exceptions.ResourceNotFoundException;
import com.assessment.inc.respositories.InventoryRepository;
import com.assessment.inc.services.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class InventoryServiceImpl implements InventoryService {


    private InventoryRepository inventoryRepository;
    public InventoryServiceImpl(InventoryRepository inventoryRepository){
        this.inventoryRepository= inventoryRepository;
    }

    @Transactional
    public List<Inventory> saveOrUpdateInventory(List<Inventory> inventories) {
        List<Inventory> savedInventory = new ArrayList<>();
        for (Inventory inventory : inventories) {
            Inventory savedInventories = inventoryRepository.save(inventory);
            savedInventory.add(savedInventories);
        }
        return savedInventory;
    }

    @Override
    public Inventory save(Inventory inventory) {
        Inventory savedInventories = inventoryRepository.save(inventory);
        return savedInventories;
    }

    @Override
        public List<Inventory> getByFromLocationAndToLocation(String fromLocation, String toLocation) {

            LocalDate currentDate = LocalDate.now(); // Example: Current date
            return inventoryRepository.findByFromLocationAndToLocationAndDateAfter(fromLocation, toLocation, currentDate);
          }
    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }
    @Override
    public Inventory getInventoryById(String trainId) {
        return inventoryRepository.findById(trainId).orElseThrow(() -> new ResourceNotFoundException("Train with given trainId  not found  for :"+trainId));

    }
    @Override
    public void deleteInventory(String trainId) {
        inventoryRepository.deleteById(trainId);
    }
}
