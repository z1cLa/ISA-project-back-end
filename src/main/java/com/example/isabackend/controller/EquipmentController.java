package com.example.isabackend.controller;

import com.example.isabackend.model.Company;
import com.example.isabackend.model.Equipment;
import com.example.isabackend.service.CompanyService;
import com.example.isabackend.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipment")
@RequiredArgsConstructor
@CrossOrigin
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping("/{id}")
    public Equipment getCompanyById(@PathVariable Integer id) {
        return equipmentService.findById(id);
    }

    @GetMapping("/all")
    public List<Equipment> getAllEquipment() {
        return equipmentService.findAll();
    }

    @PutMapping("/update/{id}")
    public Equipment updateEqupment(@PathVariable Integer id, @RequestBody Equipment updatedEquipment) {
        return equipmentService.update(id, updatedEquipment);
    }

    @PostMapping("/save")
    public Equipment saveEquipment(@RequestBody Equipment equipment) {
        return equipmentService.save(equipment);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEquipment(@PathVariable Integer id) {
        equipmentService.remove(id);
    }

    @GetMapping("/company/{companyId}")
    public List<Equipment> getEquipmentByCompanyId(@PathVariable Integer companyId) {
        return equipmentService.getEquipmentByCompanyId(companyId);
    }
}
