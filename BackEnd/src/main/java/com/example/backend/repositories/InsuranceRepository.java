package com.example.backend.repositories;

import com.example.backend.entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance,Integer> {
}
