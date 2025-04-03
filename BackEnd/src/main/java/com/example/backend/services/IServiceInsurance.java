package com.example.backend.services;

import com.example.backend.entities.Insurance;

import java.util.List;

public interface IServiceInsurance {



    Insurance addInsurance(Insurance insurance);
    void deleteInsurance(int idInsurance);
    Insurance updateInsurance(Insurance insurance);
    List<Insurance> allInsurances();
    Insurance findInsuranceById(int idInsurance);
    
    
}
