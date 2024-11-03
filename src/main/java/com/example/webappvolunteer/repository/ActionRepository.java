package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Integer> {
}
