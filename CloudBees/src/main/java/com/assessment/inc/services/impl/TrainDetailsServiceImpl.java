package com.assessment.inc.services.impl;

import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.respositories.TrainDetailsRepository;
import com.assessment.inc.services.TrainDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TrainDetailsServiceImpl implements TrainDetailsService {

    @Autowired
    private TrainDetailsRepository trainDetailsRepository;

    @Override
    @Transactional
    public List<TrainDetails> saveTrainDetails(List<TrainDetails> trainDetails) {
        System.out.println("-----------------saveTrainDetails---------->>>>");
        List<TrainDetails> savedTrainDetails = new ArrayList<>();
        for (TrainDetails trainDetail : trainDetails) {
            TrainDetails savedTrainDetail = trainDetailsRepository.save(trainDetail);
            savedTrainDetails.add(savedTrainDetail);
        }
        return savedTrainDetails;
    }


    @Override
    public List<TrainDetails> getAllTrainDetails() {
        return trainDetailsRepository.findAll();
    }

    @Override
    public TrainDetails save(TrainDetails trainDetail) {
        TrainDetails savedTrainDetails = trainDetailsRepository.save(trainDetail);
        return savedTrainDetails;
    }


    @Override
    public TrainDetails getTrainById(String trainId) {
        Optional<TrainDetails> optionalTrainDetails = trainDetailsRepository.findById(trainId);
        return optionalTrainDetails.orElse(null); // Handle if trainDetails is not found
    }


    @Override
    public List<TrainDetails> getByFromLocationAndToLocation(String fromLocation, String toLocation) {
        LocalDate currentDate = LocalDate.now();
        return trainDetailsRepository.findByFromLocationAndToLocationAndDateAfter(fromLocation, toLocation, currentDate);
    }
    @Override
    public void deleteTrainDetails(String trainId) {
        trainDetailsRepository.deleteById(trainId);
    }

    // Add more methods as needed for your application
}
