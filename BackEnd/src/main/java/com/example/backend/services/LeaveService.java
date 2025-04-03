package com.example.backend.services;

import com.example.backend.entities.Leave;
import com.example.backend.repositories.LeaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

}
