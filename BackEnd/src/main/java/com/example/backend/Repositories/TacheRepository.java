package com.example.backend.Repositories;

import com.example.backend.Entities.StatutTache;
import com.example.backend.Entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache,Long> {

        List<Tache> findByStatut(StatutTache statut);

    @Query("SELECT DISTINCT t FROM Tache t " +
           "LEFT JOIN t.responsable r " +
           "LEFT JOIN t.projet p " +
           "WHERE LOWER(t.nom) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(t.statut) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(t.priorite) LIKE LOWER(CONCAT('%', :query, '%')) " +

           "OR CAST(t.dateDebut AS string) LIKE CONCAT('%', :query, '%') " +
           "OR CAST(t.dateFin AS string) LIKE CONCAT('%', :query, '%')")
    List<Tache> searchAllAttributes(@Param("query") String query);
    }

