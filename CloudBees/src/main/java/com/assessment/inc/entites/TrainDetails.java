package com.assessment.inc.entites;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TrainDetails")
public class TrainDetails {
    @Id
    private String trainId;
    private String trainName;
    private LocalDate date;
    private String fromLocation;
    private String toLocation;
    private Double fare;
    private Integer avaliableSeats;
}
