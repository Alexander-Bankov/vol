package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.entity.Application;
import com.example.webappvolunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
