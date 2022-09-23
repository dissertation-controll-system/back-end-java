package com.masterswork.account.repository;

import com.masterswork.account.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByAccounts_Id(Long accountId);

    List<Role> findAllByAccounts_Username(String username);
}
