package com.example.backend.services;

import com.example.backend.entities.Leave;

import java.util.List;

public interface ILeaveService {
    Leave addLeave(Leave leave);
    void deleteLeave(int idLeave);
    Leave updateLeave(Leave leave);
    List<Leave> allLeaves();
    Leave findLeaveById(int idLeave);
}
