package com.assessment.inc.services.impl;

import com.assessment.inc.dto.TrainEntityDto;
import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.services.InventoryService;
import com.assessment.inc.services.TrainDetailsService;
import com.assessment.inc.services.TrainEntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainEntitiesServiceImpl implements TrainEntitiesService {


    private InventoryService inventoryService ;
    private  TrainDetailsService trainDetailsService;

    @Autowired
    public TrainEntitiesServiceImpl(InventoryService inventoryService, TrainDetailsService trainDetailsService) {
        this.inventoryService = inventoryService;
        this.trainDetailsService = trainDetailsService;
    }

    @Override
    public List<TrainEntityDto> saveOrUpdateTrainEntities(List<TrainEntityDto> inventories) {
        TrainMappingService mappingService = new TrainMappingService();
        List<Inventory> inventoryList=new ArrayList<>();
        List<TrainDetails> trainDetails=new ArrayList<>();

        for (TrainEntityDto inventory : inventories) {

            TrainDetails TtainDetail = mappingService.mapToTrainDetailsDto(inventory);
            trainDetails.add(TtainDetail);

            Inventory trainInventory = mappingService.mapToTrainInventoryDto(inventory);
            inventoryList.add(trainInventory);
        }

        inventoryService.saveOrUpdateInventory(inventoryList);
        trainDetailsService.saveTrainDetails(trainDetails);
  return inventories;
    }

    @Override
    @Transactional
    public void deleteTrainById(String trainId) {
        inventoryService.deleteInventory(trainId);
        trainDetailsService.deleteTrainDetails(trainId);
    }


   /* @Override
    public List<TrainEntities> getAllTrainEntities() {
        return List.of();
    }

    @Override
    public TrainEntities getTrainEntitiesById(String trainId) {
        return null;
    }

    @Override
    public void deleteTrainEntities(String trainId) {

    }*/
}
