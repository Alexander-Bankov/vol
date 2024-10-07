package com.example.webappvolunteer.service;

import com.example.webappvolunteer.dao.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface UserRepository extends JpaRepository<Volunteer, BigInteger> {
}
