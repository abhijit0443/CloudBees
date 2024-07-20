package com.assessment.inc.respositories;



import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.entites.TrainKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainDetailsRepository extends JpaRepository<TrainDetails, TrainKey> {
    List<TrainDetails> findByFromLocationAndToLocationAndDateAfter(String fromLocation, String toLocation, LocalDate date);;
    List<TrainDetails> findByTrainId(String trainId);
    void deleteByTrainId(String trainId);



    TrainDetails findByTrainIdAndDate(String trainId, LocalDate date);
}
