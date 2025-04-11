package com.example.backend.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Insurance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_Insurance;
    String Description;
    Date Start_Date;
    Date End_Date;
    double Amount;
    @Enumerated(EnumType.STRING)
    Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



}



