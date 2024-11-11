package com.example.webappvolunteer.service;

import com.example.webappvolunteer.dto.VolunteerDto;
import com.example.webappvolunteer.entity.Volunteer;
import com.example.webappvolunteer.enums.Role;
import com.example.webappvolunteer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

//import static com.example.webappvolunteer.config.SecurityConfig.passwordEncoder;

@Service
public class CustomUserDetailService /*implements UserDetailsService*/ {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean register(VolunteerDto volunteer) {
        String a = String.valueOf(userRepository.findByEmail(volunteer.getMail()));
        if (userRepository.findByEmail(volunteer.getMail()).isPresent()) {
            return false;
        } else {
            Volunteer vol = new Volunteer();
            vol.setMail(volunteer.getMail());
            vol.setPasswords(volunteer.getPasswords());
            //vol.setPasswords(passwordEncoder().encode(volunteer.getPasswords())); // Хэширование пароля
            vol.setLastName(volunteer.getLastName());
            vol.setFirstName(volunteer.getFirstName());
            vol.setSecondName(volunteer.getSecondName());
            vol.setBirthdate(volunteer.getBirthdate());
            vol.setAddress(volunteer.getAddress());
            vol.setPhone(volunteer.getPhone());
            vol.setVolunteerInfo(volunteer.getVolunteerInfo());
            vol.setLanguage(volunteer.getLanguage());
            vol.setRole(Role.VOLUNTEER);
            userRepository.save(vol);
            return true;
        }

    }
}
