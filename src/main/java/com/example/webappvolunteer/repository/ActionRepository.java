package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.dto.ActionInfoDto;
import com.example.webappvolunteer.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action, Long> {

    @Query("SELECT a.actionId FROM Action a WHERE a.actionName = :name")
    Optional<Long> findActionIdByName(@Param("name") String name);

    @Query(value = "SELECT a.action_name, a.action_start, a.action_end, a.status, " +
                   "STRING_AGG(e.event_name, ', ') AS event_names " +
                   "FROM action a " +
                   "JOIN event e ON a.action_id = e.action_id " +
                   "WHERE a.action_name = :name " +
                   "GROUP BY a.action_name, a.action_start, a.action_end, a.status",
            nativeQuery = true)
    List<Object[]> findActionInfoByActionName(String name);

    @Query(value = "SELECT a.action_name, a.action_start, a.action_end, a.status, " +
                   "STRING_AGG(e.event_name, ', ') AS event_names " +
                   "FROM action a " +
                   "JOIN event e ON a.action_id = e.action_id " +
                   "GROUP BY a.action_name, a.action_start, a.action_end, a.status " +
                   "ORDER BY a.action_name ASC",
            nativeQuery = true)
    List<Object[]> getSortedByName();

    @Query(value = "SELECT a.action_name, a.action_start, a.action_end, a.status, " +
                   "STRING_AGG(e.event_name, ', ') AS event_names " +
                   "FROM action a " +
                   "JOIN event e ON a.action_id = e.action_id " +
                   "GROUP BY a.action_name, a.action_start, a.action_end, a.status " +
                   "ORDER BY CASE a.status " +
                   "WHEN 'municipal' THEN 1 " +
                   "WHEN 'city' THEN 2 " +
                   "WHEN 'regional' THEN 3 " +
                   "WHEN 'national' THEN 4 " +
                   "ELSE 5 END",
            nativeQuery = true)
    List<Object[]> getSortedByStatus();

    @Query(value = "SELECT a.action_name, a.action_start, a.action_end, a.status, " +
                   "STRING_AGG(e.event_name, ', ') AS event_names " +
                   "FROM action a " +
                   "JOIN event e ON a.action_id = e.action_id " +
                   "GROUP BY a.action_name, a.action_start, a.action_end, a.status " +
                   "ORDER BY a.action_start DESC",
            nativeQuery = true)
    List<Object[]> getSortedByDate();

    @Query(value = "SELECT a.action_name, a.action_start, a.action_end, a.status, " +
                   "STRING_AGG(e.event_name, ', ') AS event_names " +
                   "FROM action a " +
                   "JOIN event e ON a.action_id = e.action_id " +
                   "GROUP BY a.action_name, a.action_start, a.action_end, a.status ",
            nativeQuery = true)
    List<Object[]> getAllACtion();


    @Query(value = "SELECT a.action_name, a.action_start, a.action_end, a.status, " +
                   "STRING_AGG(e.event_name, ', ') AS event_names " +
                   "FROM action a " +
                   "JOIN event e ON a.action_id = e.action_id " +
                   "WHERE a.status = :status " +
                   "GROUP BY a.action_name, a.action_start, a.action_end, a.status " +
                   "ORDER BY a.action_name DESC",
            nativeQuery = true)
    List<Object[]> filterStatus(@Param("status") String status);

    @Query(value = "SELECT a.action_name, a.action_start, a.action_end, a.status, " +
                   "STRING_AGG(e.event_name, ', ') AS event_names " +
                   "FROM action a " +
                   "JOIN event e ON a.action_id = e.action_id " +
                   "WHERE a.action_start BETWEEN :startDate AND :endDate " +
                   "GROUP BY a.action_name, a.action_start, a.action_end, a.status " +
                   "ORDER BY a.action_start DESC",
            nativeQuery = true)
    List<Object[]> filterDateRange(@Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate);



}
