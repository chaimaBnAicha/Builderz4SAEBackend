package com.example.backend.services;

import com.example.backend.entities.Insurance;
import com.example.backend.repositories.InsuranceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class InsuranceService implements IServiceInsurance{

    InsuranceRepository insurancerepo;

    @Override
    public Insurance addInsurance(Insurance insurance) {
        return insurancerepo.save(insurance);
    }

    @Override
    public void deleteInsurance(int idInsurance) {
        insurancerepo.deleteById(idInsurance);

    }

    @Override
    public Insurance updateInsurance(Insurance insurance) {
       return insurancerepo.save(insurance);
    }

    @Override
    public List<Insurance> allInsurances() {
        return insurancerepo.findAll();
    }

    @Override
    public Insurance findInsuranceById(int idInsurance) {
        return insurancerepo.findById(idInsurance).get();
    }
}
