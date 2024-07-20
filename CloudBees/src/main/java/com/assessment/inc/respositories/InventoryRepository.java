package com.assessment.inc.respositories;

import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.TrainKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, TrainKey> {
    List<Inventory> findByFromLocationAndToLocationAndDateAfter(String fromLocation, String toLocation, LocalDate date);;
    List<Inventory> findByTrainId(String trainId);
    void deleteByTrainId(String trainId);



    Inventory findByTrainIdAndDate(String trainId, LocalDate date);
}
