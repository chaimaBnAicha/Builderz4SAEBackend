package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String First_Name;
    String Last_Name;
    String Email;
    String Password;
    double Salary;
    Date DateOfHire;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Offer> Offers;


}
