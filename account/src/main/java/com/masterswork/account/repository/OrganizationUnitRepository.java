package com.masterswork.account.repository;

import com.masterswork.account.model.OrganizationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationUnitRepository extends JpaRepository<OrganizationUnit, Long> {
    
}
