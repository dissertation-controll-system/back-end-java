package com.masterswork.process.repository;

import com.masterswork.process.model.OrganizationUnit;
import com.masterswork.process.model.projection.OrganizationUnitParticipantsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationUnitRepository extends JpaRepository<OrganizationUnit, Long> {

    OrganizationUnitParticipantsProjection getParticipantsById(Long id);

}
