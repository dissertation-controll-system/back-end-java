package com.masterswork.auth.repository;

import com.masterswork.auth.model.Cathedra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CathedraRepository extends JpaRepository<Cathedra, Long> {
}
