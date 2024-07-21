package com.assessment.inc.controllers;


import com.assessment.inc.dto.TrainEntityDto;
import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.services.InventoryService;
import com.assessment.inc.services.TrainDetailsService;
import com.assessment.inc.services.TrainEntitiesService;
import com.assessment.inc.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(Constants.API_BASE_PATH)
public class AdministrativeController {
    private static final Logger logger = LoggerFactory.getLogger(AdministrativeController.class);

    private final InventoryService inventoryService;
    private final TrainDetailsService trainDetailsService;

    private final TrainEntitiesService trainEntitiesService;
    @Autowired
    public AdministrativeController(TrainEntitiesService trainEntitiesService, InventoryService inventoryService,TrainDetailsService trainDetailsService) {
        this.trainEntitiesService = trainEntitiesService;
        this.inventoryService=inventoryService;
        this.trainDetailsService=trainDetailsService;
    }

    @PostMapping(Constants.API_CREATE_TRAINS)
    public ResponseEntity<?> saveOrUpdateTrainEntities(@RequestBody List<TrainEntityDto> trainEntities) {
        try {
            List<TrainEntityDto> addedTrainEntities = trainEntitiesService.saveOrUpdateTrainEntities(trainEntities);
            logger.info("Saved or updated {} train entities", addedTrainEntities.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(addedTrainEntities);
        } catch (ValidationException e) {
            logger.error("Validation error while saving or updating train entities", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.VALIDATE_ERROR);
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation error while saving or updating train entities", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Constants.DATABASE_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error while saving or updating train entities", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.UNEXPECTED_ERROR);
        }
    }

    @GetMapping(Constants.API_GET_TRAINS_BY_FROM_TO_LOCATIONS)
    public ResponseEntity<List<TrainDetails>> getByFromAndToLocation(
            @PathVariable @NotBlank(message = Constants.FROM_LOCATION_NOT_BLANK) String fromLocation,
            @PathVariable @NotBlank(message = Constants.TO_LOCATION_NOT_BLANK) String toLocation) {

        logger.info("Fetching trains from '{}' to '{}'", fromLocation, toLocation);

        List<TrainDetails> trainDetails = trainDetailsService.getByFromLocationAndToLocation(fromLocation, toLocation);

        if (trainDetails.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(trainDetails);
        }
    }

    @GetMapping(Constants.API_FIND_TRAIN_ID)
    public List<TrainDetails> findByTrainId(@PathVariable @NotBlank(message = Constants.TICKET_ID_NOT_BLANK) String trainId) {
        logger.info("Fetching train details for train ID: {}", trainId);
        return trainDetailsService.findByTrainId(trainId);
    }

    @DeleteMapping(Constants.API_DELETE_TRAIN_ID)
    public ResponseEntity<Void> deleteTrainById(@PathVariable @NotBlank(message = Constants.TICKET_ID_NOT_BLANK) String trainId) {
        logger.info("Deleting train with ID: {}", trainId);
        trainEntitiesService.deleteTrainById(trainId);
        return ResponseEntity.noContent().build();
    }
}
