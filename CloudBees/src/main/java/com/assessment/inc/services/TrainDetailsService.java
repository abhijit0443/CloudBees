package com.assessment.inc.services;

import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.TrainDetails;

import java.util.List;

public interface TrainDetailsService {

    TrainDetails save(TrainDetails trainDetail);
    List<TrainDetails> saveTrainDetails(List<TrainDetails> trainDetails);

    List<TrainDetails> getAllTrainDetails();
    List<TrainDetails> getByFromLocationAndToLocation(String fromLocation, String toLocation) ;

    TrainDetails getTrainById(String trainId);

    void deleteTrainDetails(String trainId);

}