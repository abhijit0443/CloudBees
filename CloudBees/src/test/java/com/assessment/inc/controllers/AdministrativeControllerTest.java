package com.assessment.inc.controllers;

import com.assessment.inc.controllers.AdministrativeController;
import com.assessment.inc.dto.TrainEntityDto;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.services.InventoryService;
import com.assessment.inc.services.TrainDetailsService;
import com.assessment.inc.services.TrainEntitiesService;
import com.assessment.inc.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdministrativeControllerTest {

    @Mock
    private TrainEntitiesService trainEntitiesService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private TrainDetailsService trainDetailsService;

    @InjectMocks
    private AdministrativeController administrativeController;

    @Test
    public void testSaveOrUpdateTrainEntities() {

        List<TrainEntityDto> mockTrainEntities = Arrays.asList(
                new TrainEntityDto("12345", "HowYpr", LocalDate.of(2024, 7, 22), "Howrah", "YPR", 100, 20.0),
                new TrainEntityDto("22222", "KolkataExpress", LocalDate.of(2024, 7, 23), "BANGALORE", "KOLAKTA", 150, 30.0)

        );
        when(trainEntitiesService.saveOrUpdateTrainEntities(anyList())).thenReturn(mockTrainEntities);
        ResponseEntity<?> response = administrativeController.saveOrUpdateTrainEntities(mockTrainEntities);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockTrainEntities, response.getBody());
    }




    @Test
    public void testGetByFromAndToLocationEmptyResult() {
        String fromLocation = "Bangalore";
        String toLocation = "Kolkata";
        when(trainDetailsService.getByFromLocationAndToLocation(anyString(), anyString())).thenReturn(Arrays.asList());
        ResponseEntity<List<TrainDetails>> response = administrativeController.getByFromAndToLocation(fromLocation, toLocation);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetByFromAndToLocationSuccess() {
        String fromLocation = "Bangalore";
        String toLocation = "Kolkata";
        List<TrainDetails> expectedTrainDetails = Arrays.asList(new TrainDetails(), new TrainDetails());
        when(trainDetailsService.getByFromLocationAndToLocation(anyString(), anyString())).thenReturn(expectedTrainDetails);
        ResponseEntity<List<TrainDetails>> response = administrativeController.getByFromAndToLocation(fromLocation, toLocation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTrainDetails, response.getBody());
    }

    @Test
    public void testFindByTrainIdSuccess() {
        String trainId = "12345";
        when(trainDetailsService.findByTrainId(trainId)).thenReturn(Arrays.asList(new TrainDetails(), new TrainDetails()));
        List<TrainDetails> trainDetails = administrativeController.findByTrainId(trainId);
        assertEquals(2, trainDetails.size());
    }

    @Test
    public void testDeleteTrainById_Success() {
        String trainId = "12345";
        ResponseEntity<Void> response = administrativeController.deleteTrainById(trainId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(trainEntitiesService, times(1)).deleteTrainById(trainId);
    }

}
