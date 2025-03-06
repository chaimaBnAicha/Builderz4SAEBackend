package com.example.backend.repositories;

import com.example.backend.entities.Advance;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public interface AdvanceRepository extends JpaRepository<Advance, Integer>,  AdvanceRepositoryCustom {



    @Query("SELECT a FROM Advance a JOIN FETCH a.user WHERE a.user.id = :userId")
    List<Advance> findAdvancesByUserId(@Param("userId") int userId);

    @Query("SELECT a.requestDate FROM Advance a WHERE a.user.id = :userId ORDER BY a.requestDate DESC")
    List<Date> findLastAdvanceDatesByUserId(@Param("userId") int userId);


    /// Statistics
    @Query("SELECT a.status, COUNT(a) FROM Advance a GROUP BY a.status")
    List<Object[]> countAdvancesByStatus();

    @Query("SELECT MONTHNAME(a.requestDate), COUNT(a) FROM Advance a GROUP BY MONTH(a.requestDate)")
    List<Object[]> countAdvancesByMonth();

    @Query("SELECT FUNCTION('DATE_FORMAT', a.requestDate, '%Y-%m-%d'), SUM(a.amount_request) " +
            "FROM Advance a GROUP BY FUNCTION('DATE_FORMAT', a.requestDate, '%Y-%m-%d') ORDER BY a.requestDate")
    List<Object[]> findAdvancesForSinusoidal();








}
