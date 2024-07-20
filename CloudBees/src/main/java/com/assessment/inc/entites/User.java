package com.assessment.inc.entites;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="users")
public class
User {
    @Id
    private String userId;
    private String FirstName;
    private String LastName;
    private String email;
    private String phone;

}
