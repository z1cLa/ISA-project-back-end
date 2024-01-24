package com.example.isabackend.controller;

import com.example.isabackend.model.Company;
import com.example.isabackend.model.User;
import com.example.isabackend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id) {
        return companyService.findById(id);
    }

    @GetMapping("/all")
    //@PreAuthorize("hasRole('USER')")
    public List<Company> getAllCompanies() {
        return companyService.findAll();
    }

    @PutMapping("/update/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company updatedCompany) {
        return companyService.update(id, updatedCompany);
    }


    @PostMapping("/save")
    public Company saveCompany(@RequestBody Company company) {
        return companyService.save(company);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCompany(@PathVariable Integer id) {
        companyService.remove(id);
    }

    @GetMapping("/{companyId}/admins")
    public Set<User> getAdminsByCompanyId(@PathVariable Integer companyId) {
        return companyService.getAdminsByCompanyId(companyId);
    }

    @GetMapping("/companyId/{userId}")
    public Integer getCompanyIdByUserId(@PathVariable Long userId) {
        return companyService.findCompanyIdByUserId(userId);
    }
}
