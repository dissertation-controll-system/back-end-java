package com.masterswork.account.service;

import com.masterswork.account.api.dto.cathedra.CathedraCreateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.cathedra.CathedraUpdateDTO;

import java.util.List;

public interface CathedraService {

    CathedraResponseDTO createCathedraByFacultyId(Long facultyId, CathedraCreateDTO cathedraCreateDTO);

    List<CathedraResponseDTO> getAllCathedras();
    List<CathedraResponseDTO> findAllCathedrasByFacultyId(Long facultyId);

    CathedraResponseDTO updateCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO);

    CathedraResponseDTO updateCathedraByFacultyIdAndCathedraId(Long facultyId, Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO);

    CathedraResponseDTO patchCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO);

    CathedraResponseDTO patchCathedraByFacultyIdAndCathedraId(Long facultyId, Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO);

    void deleteCathedraById(Long cathedraId);

    void deleteCathedraByFacultyIdAndCathedraId(Long facultyId, Long cathedraId);

    CathedraResponseDTO getCathedraById(Long cathedraId);
    CathedraResponseDTO getCathedraByFacultyIdAndCathedraId(Long facultyId, Long cathedraId);
}
