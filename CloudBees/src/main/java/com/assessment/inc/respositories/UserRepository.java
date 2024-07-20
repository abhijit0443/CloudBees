package com.assessment.inc.respositories;


import com.assessment.inc.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Customer, String> {

}