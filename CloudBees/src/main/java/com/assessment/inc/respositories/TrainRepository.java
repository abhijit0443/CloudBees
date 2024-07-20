package com.assessment.inc.respositories;




import com.assessment.inc.entites.TrainDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<TrainDetails, String> {
}

