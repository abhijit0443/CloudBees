package com.assessment.inc.services;


import com.assessment.inc.dto.TrainEntityDto;
import com.assessment.inc.entites.TrainEntities;

import java.util.List;


public interface TrainEntitiesService {

    List<TrainEntityDto> saveOrUpdateTrainEntities(List<TrainEntityDto> trainEntities);


    public void deleteTrainById(String trainId) ;
    /*List<TrainEntities> getAllTrainEntities();


    TrainEntities getTrainEntitiesById(String trainId);

    void deleteTrainEntities(String trainId);*/
}