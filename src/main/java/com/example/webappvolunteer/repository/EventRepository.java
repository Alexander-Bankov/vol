package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.dto.EventInfoDto;
import com.example.webappvolunteer.entity.Action;
import com.example.webappvolunteer.entity.Event;
import com.example.webappvolunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT a.actionId FROM Action a WHERE a.actionName = :name")
    Optional<Long> findActionIdByName(@Param("name") String name);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.action_name = :name")
    List<Object[]> findEventsByActionName(@Param("name") String name);
}
