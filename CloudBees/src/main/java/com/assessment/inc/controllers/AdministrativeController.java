package com.assessment.inc.controllers;


import com.assessment.inc.dto.TrainEntityDto;
import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.services.InventoryService;
import com.assessment.inc.services.TrainDetailsService;
import com.assessment.inc.services.TrainEntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trains/")
public class AdministrativeController {

    private final InventoryService inventoryService;
    private final TrainDetailsService trainDetailsService;

    private final TrainEntitiesService trainEntitiesService;
    @Autowired
    public AdministrativeController(TrainEntitiesService trainEntitiesService, InventoryService inventoryService,TrainDetailsService trainDetailsService) {
        this.trainEntitiesService = trainEntitiesService;
        this.inventoryService=inventoryService;
        this.trainDetailsService=trainDetailsService;
    }



    @PostMapping("/createTrains")
    public ResponseEntity<?> saveOrUpdateTrainEntities(@RequestBody List<TrainEntityDto> trainEntities) {


        try {
            List<TrainEntityDto> addedTrainEntities = trainEntitiesService.saveOrUpdateTrainEntities(trainEntities);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedTrainEntities);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: ");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Database error: ");
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: ");
        }
    }


    @GetMapping("/from/{fromLocation}/to/{toLocation}")
    public ResponseEntity<List<TrainDetails>> getByFromAndToLocation(
            @PathVariable String fromLocation,
            @PathVariable String toLocation) {

        List<TrainDetails> trainDetails = trainDetailsService.getByFromLocationAndToLocation(fromLocation, toLocation);

        if (trainDetails.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(trainDetails);
        }
    }
    @GetMapping("/{trainId}")
    public TrainDetails getTrainById(@PathVariable String trainId) {
        return trainDetailsService.getTrainById(trainId);

    }

    @DeleteMapping("/{trainId}")
    public ResponseEntity<Void> deleteTrainById(@PathVariable String trainId) {
        trainEntitiesService.deleteTrainById(trainId);
        return ResponseEntity.noContent().build();
    }
}
