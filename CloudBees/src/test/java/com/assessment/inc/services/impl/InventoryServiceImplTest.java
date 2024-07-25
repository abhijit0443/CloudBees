package com.assessment.inc.services.impl;

import com.assessment.inc.entites.Inventory;
import com.assessment.inc.respositories.InventoryRepository;
import com.assessment.inc.services.InventoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceImplTest {
/*
    @Mock
    InventoryServiceImpl inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;


    @Test
    public void saveOrUpdateInventory() {
      List<Inventory> inventories = new ArrayList<>();
      inventories.add(new Inventory());
      when(inventoryRepository.save(any(Inventory.class))).thenReturn(new Inventory());
      inventoryService.saveOrUpdateInventory(inventories);

    }
*/


    @InjectMocks
    private InventoryServiceImpl inventoryService;
    @Mock
    private InventoryRepository inventoryRepository;
    Inventory mockInventory = new Inventory();
    List<Inventory> inventories = new ArrayList<>();
    @Before
    public void setup() {

        mockInventory.setTrainId("12345");
        mockInventory.setTrainName("HowrahYashwanthpur");
        mockInventory.setDate(LocalDate.of(2024, 8, 30));
        mockInventory.setSeats(100);
        mockInventory.setAvaliableSeats(100);
        mockInventory.setFare(20.0);

    }

    @Test
    public void testSaveOrUpdateInventory() {

        when(inventoryRepository.save(mockInventory)).thenReturn(new Inventory());

        inventories.add(mockInventory);
        List<Inventory> savedInventories = inventoryService.saveOrUpdateInventory(inventories);
        verify(inventoryRepository, times(inventories.size())).save(any(Inventory.class));
        assertEquals(inventories.size(), savedInventories.size());
    }

    @Test
    public void save() {
        when(inventoryRepository.save(mockInventory)).thenReturn(new Inventory());
        Inventory savedInventories = inventoryService.save(mockInventory);
        assertEquals(savedInventories.getTrainId(), savedInventories.getTrainId());
    }



    @Test
    public void getAllInventories() {
    }

    @Test
    public void findByTrainId() {
    }

    @Test
    public void deleteInventory() {
    }

    @Test
    public void findByTrainIdAndDate() {
    }
}