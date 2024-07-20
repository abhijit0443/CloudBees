package com.assessment.inc.respositories;



import com.assessment.inc.entites.TrainDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainDetailsRepository extends JpaRepository<TrainDetails, String> {
    List<TrainDetails> findByFromLocationAndToLocationAndDateAfter(String fromLocation, String toLocation, LocalDate date);;

}
