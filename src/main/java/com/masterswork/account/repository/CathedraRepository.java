package com.masterswork.account.repository;

import com.masterswork.account.model.Cathedra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CathedraRepository extends JpaRepository<Cathedra, Long> {

    List<Cathedra> findAllByFacultyId(Long facultyId);

    List<Cathedra> findAllByUsers_Id(Long userId);

    Optional<Cathedra> findByFacultyIdAndId(Long facultyId, Long cathedraId);
}
