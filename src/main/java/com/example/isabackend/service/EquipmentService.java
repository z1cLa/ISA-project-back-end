package com.example.isabackend.service;

import com.example.isabackend.model.Company;
import com.example.isabackend.model.Equipment;
import com.example.isabackend.model.User;
import com.example.isabackend.repository.CompanyRepository;
import com.example.isabackend.repository.EquipmentRepository;
import com.example.isabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }


    public Equipment findById(Integer id) { return equipmentRepository.findById(id).orElseGet(null);}

    public List<Equipment> findAll() {return equipmentRepository.findAll();}

    public Equipment save(Equipment exam) {
        return equipmentRepository.save(exam);
    }

    public void remove(Integer id) {
        equipmentRepository.deleteById(id);
    }

    public Equipment update(Integer id, Equipment updatedEquipment) {
        Equipment existingEquipment = equipmentRepository.findById(id).orElse(null);

        if (existingEquipment != null) {

            existingEquipment.setEquipmentName(updatedEquipment.getEquipmentName());
            existingEquipment.setEquipmentType(updatedEquipment.getEquipmentType());
            existingEquipment.setEquipmentDescription(updatedEquipment.getEquipmentDescription());
            existingEquipment.setEquipmentPrice(updatedEquipment.getEquipmentPrice());

            return equipmentRepository.save(existingEquipment);
        }

        return null;
    }

    public List<Equipment> getEquipmentByCompanyId(Integer companyId) {
        return equipmentRepository.findByCompanyId(companyId);
    }
}


