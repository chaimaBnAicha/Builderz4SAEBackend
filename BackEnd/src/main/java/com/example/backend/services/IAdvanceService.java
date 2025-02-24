package com.example.backend.services;

import com.example.backend.entities.Advance;

import java.util.List;

public interface IAdvanceService {
    Advance addAdvance(Advance advance);
    void deleteAdvance(int idAdvance);
    Advance updateAdvance(Advance advance);
    List<Advance> allAdvances();
    Advance findAdvanceById(int idAdvance);
    List<Advance> getAllAdvancesByUser(int userId);
    boolean canApproveAdvance(int userId, int advanceId);
    //Advance updateAdvanceStatus(int id, String status);
}
