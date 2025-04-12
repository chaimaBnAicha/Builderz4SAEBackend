package com.example.backend.repositories;

import com.example.backend.entities.Leave;
import com.example.backend.entities.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    @Query("SELECT COUNT(l) = 0 FROM Leave l " +
            "WHERE l.user.id = :userId " +
            "AND l.status = :status " +
            "AND l.start_date <= :endDate " +
            "AND l.end_date >= :startDate")
    boolean canAcceptLeaveRequest(@Param("userId") int userId,
                                  @Param("status") LeaveStatus status,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate);
}
