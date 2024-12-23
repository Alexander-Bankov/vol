package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.dto.EventInfoDto;
import com.example.webappvolunteer.entity.Action;
import com.example.webappvolunteer.entity.Event;
import com.example.webappvolunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT a.actionId FROM Action a WHERE a.actionName = :name")
    Optional<Long> findActionIdByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("UPDATE Event e SET e.volunteerCount = e.volunteerCount + 1 WHERE e.eventId = :eventId")
    void incrementVolunteerCount(@Param("eventId") Long eventId);

    @Transactional
    @Modifying
    @Query("UPDATE Event e SET e.volunteerCount = e.volunteerCount - 1 WHERE e.eventId = :eventId")
    void deleteVolunteerCount(@Param("eventId") Long eventId);

    @Query("SELECT e.volunteerCount FROM Event e WHERE e.eventId = :eventId")
    Optional<Integer> findActionKolvoByName(@Param("eventId") Long eventId);

    @Query("SELECT e.maxVolunteerCount FROM Event e WHERE e.eventId = :eventId")
    Optional<Integer> findActionMaxKolvoByName(@Param("eventId") Long eventId);

    @Query("SELECT v.eventId FROM Event v WHERE v.eventName = :name")
    Optional<Long> findEventIdByName(String name);

    @Query("SELECT v.eventId FROM Event v WHERE v.eventName = :name and v.actionName = :actionName")
    Optional<Long> findEventIdsByName(String name,String actionName);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e")
    List<Object[]> findAllEvents();

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.action_name = :name")
    List<Object[]> findEventsByActionName(@Param("name") String name);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.event_name = :name")
    List<Object[]> findEventsByEventName(@Param("name") String name);

    @Query(nativeQuery = true,
            value = "SELECT e.event_name, e.action_name, e.place, e.date_and_time_start, e.date_and_time_end, e.volunteer_count, e.max_volunteer_count " +
                    "FROM event e WHERE e.event_name ILIKE CONCAT(:name, '%')")
    List<Object[]> findEventsByEventNameLike(@Param("name") String name);

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
