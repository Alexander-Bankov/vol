package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.entity.Application;
import com.example.webappvolunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
