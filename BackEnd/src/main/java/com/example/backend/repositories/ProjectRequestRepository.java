package com.example.backend.repositories;

import com.example.backend.entities.Request;
import com.example.backend.entities.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUserId(Long userId);
    List<Request> findByStatus(Statut status);
    List<Request> findByProjectNameContainingIgnoreCase(String project_name);
}