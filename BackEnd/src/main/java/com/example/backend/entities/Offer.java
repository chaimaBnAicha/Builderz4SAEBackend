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
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_offer;
    String Title;
    String Description;
    Date Start_Date;
    Date End_Date;
    @Enumerated(EnumType.STRING)
    TypeOffer Typeoffer;


}
