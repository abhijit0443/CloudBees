package com.assessment.inc.respositories;


import com.assessment.inc.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}