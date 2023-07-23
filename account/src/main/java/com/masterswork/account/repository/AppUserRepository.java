package com.masterswork.account.repository;

import com.masterswork.account.model.AppUser;
import com.masterswork.account.model.enumeration.PersonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByAccount_Username(String username);

    Page<AppUser> findAllByType(PersonType type, Pageable pageable);

    List<AppUser> findAllByIdIn(Set<Long> id);

    Page<AppUser> findAppUserByOrganizationUnits_OrganizationUnit_Id(Long organizationId, Pageable pageable);

    List<AppUser> findAppUserByOrganizationUnits_Id_OrganizationIdIn(Set<Long> organizationIds);
}
