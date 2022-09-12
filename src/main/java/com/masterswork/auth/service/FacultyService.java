package com.masterswork.auth.service;

import com.masterswork.auth.api.dto.faculty.FacultyCreateDTO;
import com.masterswork.auth.api.dto.faculty.FacultyResponseDTO;
import com.masterswork.auth.api.dto.faculty.FacultyUpdateDTO;

import java.util.List;

public interface FacultyService {

    FacultyResponseDTO createFaculty(FacultyCreateDTO facultyResponseDTO);

    FacultyResponseDTO updateFaculty(Long id, FacultyUpdateDTO facultyResponseDTO);

    FacultyResponseDTO patchFaculty(Long id, FacultyUpdateDTO facultyResponseDTO);

    List<FacultyResponseDTO> getAllFaculties();

    FacultyResponseDTO getFaculty(Long id);

    void deleteFaculty(Long id);
}
