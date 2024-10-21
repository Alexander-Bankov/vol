package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Volunteer, Long> {
    @Query("SELECT v FROM Volunteer v WHERE v.eMail = ?1")
    Optional<Volunteer> findByEmail(String email);
}
