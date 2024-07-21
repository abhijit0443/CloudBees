package com.assessment.inc.controllers;

import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.services.TrainDetailsService;
import com.assessment.inc.services.TrainEntitiesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdministrativeControllerTest {

    @Mock
    private TrainEntitiesService trainEntitiesService;

    @Mock
    private TrainDetailsService trainDetailsService;

    @InjectMocks
    private AdministrativeController controller;

    private List<TrainDetails> mockTrainDetails;

    @BeforeEach
    void setUp() {
        mockTrainDetails = Collections.singletonList(new TrainDetails());
    }

    @Test
    void testGetByFromAndToLocationOk() {
        when(trainDetailsService.getByFromLocationAndToLocation(anyString(), anyString())).thenReturn(mockTrainDetails);
        ResponseEntity<List<TrainDetails>> response = controller.getByFromAndToLocation("fromLocation", "toLocation");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTrainDetails, response.getBody());
        verify(trainDetailsService, times(1)).getByFromLocationAndToLocation(eq("fromLocation"), eq("toLocation"));
    }

    @Test
    void testGetByFromAndToLocationNoContent() {
        when(trainDetailsService.getByFromLocationAndToLocation(anyString(), anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<TrainDetails>> response = controller.getByFromAndToLocation("fromLocation", "toLocation");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(trainDetailsService, times(1)).getByFromLocationAndToLocation(eq("fromLocation"), eq("toLocation"));
    }

    @Test
    void testFindByTrainId() {
        when(trainDetailsService.findByTrainId(anyString())).thenReturn(mockTrainDetails);
        List<TrainDetails> result = controller.findByTrainId("trainId");
        assertEquals(mockTrainDetails, result);
        verify(trainDetailsService, times(1)).findByTrainId(eq("trainId"));
    }

    @Test
    void testDeleteTrainById() {
        ResponseEntity<Void> response = controller.deleteTrainById("trainId");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(trainEntitiesService, times(1)).deleteTrainById(eq("trainId"));
    }


}
