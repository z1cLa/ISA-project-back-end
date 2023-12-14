package com.example.isabackend.controller;

import com.example.isabackend.dto.CompanyDTO;
import com.example.isabackend.mapper.CompanyMapper;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.User;
import com.example.isabackend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> allCompaniesDTO =new ArrayList<>();
        List<Company> allCompanies =new ArrayList<>();
        allCompanies = companyService.findAll();
        CompanyMapper companyMapper = new CompanyMapper();
        allCompaniesDTO = companyMapper.CompanyListToDTO(allCompanies);
        return new ResponseEntity<>(allCompaniesDTO, HttpStatus.OK);
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
}
