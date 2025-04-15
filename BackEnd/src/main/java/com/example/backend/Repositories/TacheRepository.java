package com.example.backend.Repositories;

import com.example.backend.Entities.StatutTache;
import com.example.backend.Entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache,Long> {

        List<Tache> findByStatut(StatutTache statut);
    }

