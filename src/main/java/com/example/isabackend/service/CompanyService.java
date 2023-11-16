package com.example.isabackend.service;

import com.example.isabackend.model.Company;
import com.example.isabackend.model.User;
import com.example.isabackend.repository.CompanyRepository;
import com.example.isabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;


    public Company findById(Integer id) { return companyRepository.findById(id).orElseGet(null);}

    public List<Company> findAll() {return companyRepository.findAll();}

    public Company save(Company exam) {
        return companyRepository.save(exam);
    }

    public void remove(Integer id) {
        companyRepository.deleteById(id);
    }

    public Company update(Integer id, Company updatedCompany) {
        Company existingCompany = companyRepository.findById(id).orElse(null);

        if (existingCompany != null) {

            existingCompany.setCompanyName(updatedCompany.getCompanyName());
            existingCompany.setAddress(updatedCompany.getAddress());
            existingCompany.setDescription(updatedCompany.getDescription());
            existingCompany.setAverageGrade(updatedCompany.getAverageGrade());
            existingCompany.setFreeAppointments(updatedCompany.getFreeAppointments());

            return companyRepository.save(existingCompany);
        }

        return null;
    }

}
