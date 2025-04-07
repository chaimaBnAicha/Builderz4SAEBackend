package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Date start_date;
    Date end_date;
    String reason;
    String documentAttachement;
    @Enumerated(EnumType.STRING)
    LeaveType type;
    @Enumerated(EnumType.STRING)
    LeaveStatus status;

}
