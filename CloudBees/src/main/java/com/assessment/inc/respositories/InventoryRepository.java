package com.assessment.inc.respositories;

import com.assessment.inc.entites.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
    List<Inventory> findByFromLocationAndToLocationAndDateAfter(String fromLocation, String toLocation, LocalDate date);;

}
