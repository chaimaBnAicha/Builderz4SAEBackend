package com.example.backend.controllers;


import com.example.backend.entities.Leave;
import com.example.backend.entities.LeaveStatus;
import com.example.backend.entities.LeaveType;
import com.example.backend.services.ILeaveService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/leave")
@CrossOrigin(origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class LeaveController {


    ILeaveService leaveService;
    @GetMapping("/retrieve-all-leave")
    public List<Leave> getLeaves() {
        List<Leave> listLeaves = leaveService.allLeaves();
        return listLeaves;
    }


    @PostMapping("/add-leave")
    public Leave addLeave(@RequestBody Leave l) {
        Leave leave = leaveService.addLeave(l);
        return leave;
    }
    @DeleteMapping("/remove-leave/{leave-id}")
    public void removeLeave(@PathVariable("leave-id") int Id) {
        leaveService.deleteLeave(Id);
    }

    @PutMapping("/modify-Leave")
    public Leave modifyLeave(@RequestBody Leave l) {
        Leave leave = leaveService.updateLeave(l);
        return leave;
    }
    @GetMapping("/retrieve-leave/{leave-id}")
    public Leave retrieveLeave(@PathVariable("leave-id") int Id) {
        Leave leave = leaveService.findLeaveById(Id);
        return leave;
    }
    @GetMapping("/can-accept")
    public boolean canAcceptLeave(
            @RequestParam int userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam LeaveType type,
            @RequestParam(required = false) String document,
            @RequestParam LeaveStatus status
    ) {
        return leaveService.canAcceptLeave(userId, startDate, endDate, type, document, status);
    }

}
