package com.masterswork.account.service;

import com.masterswork.account.api.dto.cathedra.CathedraCreateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.cathedra.CathedraUpdateDTO;

import java.util.List;

public interface CathedraService {

    CathedraResponseDTO createCathedraByFacultyId(Long facultyId, CathedraCreateDTO cathedraCreateDTO);

    List<CathedraResponseDTO> getAllCathedras();

    List<CathedraResponseDTO> getAllCathedrasByAppUserId(Long appUserId);
    List<CathedraResponseDTO> findAllCathedrasByFacultyId(Long facultyId);

    CathedraResponseDTO updateCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO);

    CathedraResponseDTO assignCathedraToFaculty(Long facultyId, Long cathedraId);

    CathedraResponseDTO patchCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO);

    void deleteCathedraById(Long cathedraId);

    CathedraResponseDTO getCathedraById(Long cathedraId);
    CathedraResponseDTO getCathedraByFacultyIdAndCathedraId(Long facultyId, Long cathedraId);
}
