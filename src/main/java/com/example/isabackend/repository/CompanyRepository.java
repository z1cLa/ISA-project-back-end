package com.example.isabackend.repository;

import com.example.isabackend.model.Company;
import com.example.isabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Query("SELECT c.admins FROM Company c WHERE c.id = :companyId")
    Set<User> findAdminsByCompanyId(@Param("companyId") Integer companyId);

    @Query("SELECT c.id FROM Company c JOIN c.admins u WHERE u.id = :userId")
    Integer findCompanyIdByUserId(@Param("userId") Long userId);

}
