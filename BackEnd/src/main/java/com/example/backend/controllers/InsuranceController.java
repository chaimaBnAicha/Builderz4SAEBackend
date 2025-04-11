package com.example.backend.controllers;


import com.example.backend.entities.Insurance;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.IServiceInsurance;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Insurance")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})



public class InsuranceController {



   IServiceInsurance InsuranceService;
    
    UserRepository userRepository;
    @GetMapping("/retrieve-all-Insurances")
    public List<Insurance> getInsurances() {
        List<Insurance> listInsurances = InsuranceService.allInsurances();
        return listInsurances;
    }

    @GetMapping("/retrieve-Insurance/{Insurance-id}")
    public Insurance retrieveInsurance(@PathVariable("Insurance-id") int Id) {
        Insurance Insurance = InsuranceService.findInsuranceById(Id);
        return Insurance;
    }


    @PostMapping("/add-Insurance")
    public Insurance addInsurance(@RequestBody Insurance a) {
        Insurance Insurance = InsuranceService.addInsurance(a);
        return Insurance;
    }


    @DeleteMapping("/remove-Insurance/{Insurance-id}")
    public void removeInsurance(@PathVariable("Insurance-id") int Id) {
        InsuranceService.deleteInsurance(Id);
    }
    @PutMapping("/modify-Insurance")
    public Insurance modifyInsurance(@RequestBody Insurance a) {
        Insurance Insurance = InsuranceService.updateInsurance(a);
        return Insurance;
    }
    
    
    
    
    
    
    
    
}
