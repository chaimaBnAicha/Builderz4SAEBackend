package com.example.backend.repositories;

import com.example.backend.entities.Advance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdvanceRepository extends JpaRepository<Advance, Integer> {
    @Query("SELECT a FROM Advance a JOIN FETCH a.user WHERE a.user.id = :userId")
    List<Advance> findAdvancesByUserId(@Param("userId") int userId);

    @Query("SELECT a.requestDate FROM Advance a WHERE a.user.id = :userId ORDER BY a.requestDate DESC")
    List<Date> findLastAdvanceDatesByUserId(@Param("userId") int userId);




}
