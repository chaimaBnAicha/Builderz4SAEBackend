package com.example.backend.services;

import com.example.backend.entities.Leave;
import com.example.backend.entities.LeaveStatus;
import com.example.backend.entities.LeaveType;
import com.example.backend.repositories.LeaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor

public class LeaveService implements ILeaveService{
    LeaveRepository leaveRepository;

    @Override
    public Leave addLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

    @Override
    public void deleteLeave(int idLeave) {

        leaveRepository.deleteById(idLeave);
    }

    @Override
    public Leave updateLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

    @Override
    public List<Leave> allLeaves() {
        return leaveRepository.findAll();
    }
    @Override
    public Leave findLeaveById(int idLeave) {
        return leaveRepository.findById(idLeave).get();
    }

    @Override
    public boolean canAcceptLeave(int userId, Date startDate, Date endDate,
                                  LeaveType type, String documentAttachment, LeaveStatus status) {

        // Date logic
        if (startDate.after(endDate)) return false;
        if (startDate.before(new Date())) return false;

        // Medical leave check (if "Sick" leave requires a document)
        if (type == LeaveType.Sick && (documentAttachment == null || documentAttachment.isEmpty())) {
            return false;
        }

        // Check for overlap with other approved leaves
        return leaveRepository.canAcceptLeaveRequest(
                userId,
                LeaveStatus.Approved,
                startDate,
                endDate
        );
    }


}
