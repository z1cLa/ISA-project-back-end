package com.example.isabackend.mapper;

import com.example.isabackend.dto.CompanyDTO;
import com.example.isabackend.dto.UserDTO;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyMapper {
    public List<CompanyDTO> CompanyListToDTO(List<Company> companyList) {
        List<CompanyDTO> allCompaniesDTO =new ArrayList<>();
        for (Company company : companyList) {
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setId(company.getId());
            companyDTO.setCompanyName(company.getCompanyName());
            companyDTO.setAddress(company.getAddress());
            companyDTO.setDescription(company.getDescription());
            companyDTO.setAverageGrade(company.getAverageGrade());
            companyDTO.setAppointmentId(company.getAppointmentId());
            companyDTO.setAdmins(company.getAdmins());
            allCompaniesDTO.add(companyDTO);
        }
        return allCompaniesDTO;
    }
}
