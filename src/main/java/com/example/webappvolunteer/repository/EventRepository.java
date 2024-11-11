package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.dto.EventInfoDto;
import com.example.webappvolunteer.entity.Action;
import com.example.webappvolunteer.entity.Event;
import com.example.webappvolunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT a.actionId FROM Action a WHERE a.actionName = :name")
    Optional<Long> findActionIdByName(@Param("name") String name);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.action_name = :name")
    List<Object[]> findEventsByActionName(@Param("name") String name);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.action_name = :name ORDER BY e.date_and_time_start DESC")
    List<Object[]> eventSortedByStartDateDesc(@Param("name") String name);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.action_name = :name ORDER BY e.event_name ASC")
    List<Object[]> eventSortedByNameAsc(@Param("name") String name);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.action_name = :name ORDER BY e.max_volunteer_count DESC")
    List<Object[]> eventSortedByMaxVolunteerCountDesc(@Param("name") String name);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.action_name = :name AND e.date_and_time_start BETWEEN :startDate AND :endDate ORDER BY e.date_and_time_start DESC")
    List<Object[]> filterDateRange(@Param("name") String name,
                                                      @Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.action_name = :name AND e.max_volunteer_count <= :maxVolunteerCount ORDER BY e.volunteer_count ASC")
    List<Object[]> filterMaxVolunteerCount(@Param("name") String name,
                                                              @Param("maxVolunteerCount") int maxVolunteerCount);


}
