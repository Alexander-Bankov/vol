package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Volunteer, Long> {
    @Query("SELECT v FROM Volunteer v WHERE v.mail = :email")
    Optional<Volunteer> findByEmail(String email);
}


//@Query(nativeQuery = true,
//        value = "SELECT v.e_mail FROM volunteer v WHERE v.e_mail =:email ")
//Optional<String> findByEmailforRegist(String email);
//
//@Query(nativeQuery = true,
//        value = "SELECT v.e_mail FROM volunteer v WHERE v.e_mail =:email ")
//Optional<String> findByEmailforRegist(String email);