package com.assessment.inc.services.impl;

import com.assessment.inc.dto.TrainDetailsDto;
import com.assessment.inc.dto.TrainEntityDto;
import com.assessment.inc.dto.TrainInventoryDto;
import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainDetails;

import java.time.LocalDate;

public class TrainMappingService {

    public TrainDetails mapToTrainDetailsDto(TrainEntityDto trainEntityDto) {
        TrainDetails trainDetailsDto = new TrainDetails();
        trainDetailsDto.setTrainId(trainEntityDto.getTrainId());
        trainDetailsDto.setTrainName(trainEntityDto.getTrainName());
        trainDetailsDto.setDate(trainEntityDto.getDate());
        trainDetailsDto.setFromLocation(trainEntityDto.getFromLocation());
        trainDetailsDto.setToLocation(trainEntityDto.getToLocation());
        trainDetailsDto.setFare(trainEntityDto.getFare());
        trainDetailsDto.setAvaliableSeats(trainEntityDto.getSeats());
        return trainDetailsDto;

    }


    public Inventory mapToTrainInventoryDto(TrainEntityDto trainEntityDto) {
        Inventory trainInventoryDto = new Inventory();
        trainInventoryDto.setTrainId(trainEntityDto.getTrainId());
        trainInventoryDto.setTrainName(trainEntityDto.getTrainName());
        trainInventoryDto.setDate(trainEntityDto.getDate());
        trainInventoryDto.setFromLocation(trainEntityDto.getFromLocation());
        trainInventoryDto.setToLocation(trainEntityDto.getToLocation());
        trainInventoryDto.setSeats(trainEntityDto.getSeats());
        trainInventoryDto.setFare(trainEntityDto.getFare());
        trainInventoryDto.setAvaliableSeats(trainEntityDto.getSeats()); // You can set this value based on actual logic
        return trainInventoryDto;
    }
}