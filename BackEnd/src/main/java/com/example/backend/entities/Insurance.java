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


    // 🧠 Statut dynamique : non stocké en base
    @Transient
    public InsuranceStatus getStatus() {
        Date today = new Date();
        return End_Date.before(today) ? InsuranceStatus.EXPIRED : InsuranceStatus.VALID;
    }


}



