package com.example.isabackend.controller;

import com.example.isabackend.model.Company;
import com.example.isabackend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
@CrossOrigin
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id) {
        return companyService.findById(id);
    }

    @GetMapping("/all")
    public List<Company> getAllCompanies() {
        return companyService.findAll();
    }

    @PutMapping("/update/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company updatedCompany) {
        return companyService.update(id, updatedCompany);
    }
}
