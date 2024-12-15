package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.dto.ShowApplicationDto;
import com.example.webappvolunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Volunteer, Long> {
    @Query("SELECT v FROM Volunteer v WHERE v.mail = :email")
    Optional<Volunteer> findByEmail(String email);

    @Query("SELECT v FROM Volunteer v WHERE v.mail ILIKE CONCAT(:email, '%')")
    Optional<Volunteer> findByEmailLike(@Param("email") String email);

    @Query("SELECT v FROM Volunteer v")
    Optional<List<Volunteer>> findAllVolunteers();

    @Query("SELECT v.volunteerId FROM Volunteer v WHERE v.mail = :email")
    Optional<Long> findIdByEmail(String email);

    @Query("SELECT v.role FROM Volunteer v WHERE v.mail = :email")
    Optional<String> findRoleByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE volunteer SET last_name = :lastName, first_name = :firstName, second_name = :secondName, " +
                   "birthdate = :birthdate, addres = :address, phone = :phone, volunteer_info = :volunteerInfo, languages = :language " +
                   "WHERE e_mail = :mail", nativeQuery = true)
    void updateVolunteer(@Param("lastName") String lastName,
                         @Param("firstName") String firstName,
                         @Param("secondName") String secondName,
                         @Param("birthdate") LocalDate birthdate,
                         @Param("address") String address,
                         @Param("phone") String phone,
                         @Param("volunteerInfo") String volunteerInfo,
                         @Param("language") String language,
                         @Param("mail") String mail);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO application (event_id, action_id, volunteer_id, status_application) VALUES (:eventId, :actionId, :volunteerId, false)", nativeQuery = true)
    void createApplication(@Param("eventId") BigInteger eventId,
                           @Param("actionId") BigInteger actionId,
                           @Param("volunteerId") BigInteger volunteerId);
    @Query(nativeQuery = true,
            value = "SELECT act.action_name, ev.event_name, vol.e_mail, ap.status_application " +
                    "FROM application ap " +
                    "LEFT JOIN volunteer vol ON ap.volunteer_id = vol.volunteer_id " +
                    "LEFT JOIN action act ON ap.action_id = act.action_id " +
                    "LEFT JOIN event ev ON ap.event_id = ev.event_id " +
                    "WHERE vol.e_mail = :mail " + // добавили пробел перед ORDER BY
                    "ORDER BY CASE ap.status_application " +
                    "WHEN 'INPROCESS' THEN 1 " +
                    "WHEN 'ACCEPT' THEN 2 " +
                    "WHEN 'REJECT' THEN 3 " +
                    "END")
    List<Object[]> ShowApplication(String mail);

}


//@Query(nativeQuery = true,
//        value = "SELECT v.e_mail FROM volunteer v WHERE v.e_mail =:email ")
//Optional<String> findByEmailforRegist(String email);
//
//@Query(nativeQuery = true,
//        value = "SELECT v.e_mail FROM volunteer v WHERE v.e_mail =:email ")
//Optional<String> findByEmailforRegist(String email);