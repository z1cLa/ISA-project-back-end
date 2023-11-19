package com.example.isabackend.repository;

import com.example.isabackend.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    List<Equipment> findByCompanyId(Integer companyId);
}
