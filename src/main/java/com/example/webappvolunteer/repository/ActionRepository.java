package com.example.webappvolunteer.repository;

import com.example.webappvolunteer.dto.ActionInfoDto;
import com.example.webappvolunteer.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
