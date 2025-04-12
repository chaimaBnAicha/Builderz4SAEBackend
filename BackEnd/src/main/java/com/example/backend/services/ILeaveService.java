package com.example.backend.services;

import com.example.backend.entities.Leave;
import com.example.backend.entities.LeaveStatus;
import com.example.backend.entities.LeaveType;

import java.util.Date;
import java.util.List;

public interface ILeaveService {
    Leave addLeave(Leave leave);
    void deleteLeave(int idLeave);
    Leave updateLeave(Leave leave);
    List<Leave> allLeaves();
    Leave findLeaveById(int idLeave);
    public boolean canAcceptLeave(int userId, Date startDate, Date endDate,
                                  LeaveType type, String documentAttachment, LeaveStatus status);}
