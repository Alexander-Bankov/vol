package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.entity.Application;
import com.example.webappvolunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    void deleteByApplicationId(BigInteger applicationId);

    @Query(nativeQuery = true,
    value = "select ap.application_id from application ap\n" +
            "    left join volunteer vol on ap.volunteer_id = vol.volunteer_id\n" +
            "    left join action act on ap.action_id = act.action_id\n" +
            "    left join event ev on ap.event_id = ev.event_id\n" +
            "WHERE vol.volunteer_id = :mailId and act.action_id = :actionNameId and ev.event_id = :eventNameId")
    List<BigInteger> findApplicationIdsByEmailAndActionAndEvent(
            @Param("mailId") Long mailId,
            @Param("actionNameId") Long actionNameId,
            @Param("eventNameId") Long eventNameId);

    @Query(nativeQuery = true,
            value = "select ap.application_id from application ap\n" +
                    "    left join volunteer vol on ap.volunteer_id = vol.volunteer_id\n" +
                    "    left join action act on ap.action_id = act.action_id\n" +
                    "    left join event ev on ap.event_id = ev.event_id\n")
    List<BigInteger> findAllApplication();

    @Query(nativeQuery = true,
            value = "SELECT act.action_name, ev.event_name, vol.e_mail, ap.status_application " +
                    "FROM application ap " +
                    "LEFT JOIN volunteer vol ON ap.volunteer_id = vol.volunteer_id " +
                    "LEFT JOIN action act ON ap.action_id = act.action_id " +
                    "LEFT JOIN event ev ON ap.event_id = ev.event_id " +
                    "ORDER BY CASE ap.status_application " +
                    "WHEN 'INPROCESS' THEN 1 " +
                    "WHEN 'ACCEPT' THEN 2 " +
                    "WHEN 'REJECT' THEN 3 " +
                    "END")
    List<Object[]> ShowApplication();

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE application " +
                    "SET status_application = :status " +
                    "WHERE application_id = :applicationId")
    void updateStatus(
            @Param("applicationId") Long applicationId,
            @Param("status") String status);

    @Query(nativeQuery = true,
            value = "select ap.application_id from application ap\n" +
                    "    left join volunteer vol on ap.volunteer_id = vol.volunteer_id\n" +
                    "    left join action act on ap.action_id = act.action_id\n" +
                    "    left join event ev on ap.event_id = ev.event_id\n" +
                    "WHERE vol.volunteer_id = :mailId and act.action_id = :actionNameId and ev.event_id = :eventNameId")
    Long findApplicationIdByEmailAndActionAndEvent(
            @Param("mailId") Long mailId,
            @Param("actionNameId") Long actionNameId,
            @Param("eventNameId") Long eventNameId);


}
