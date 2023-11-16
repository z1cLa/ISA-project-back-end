package com.example.isabackend.controller;

import com.example.isabackend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
@CrossOrigin
public class CompanyController {
    @Autowired
    private CompanyService companyService;

}
