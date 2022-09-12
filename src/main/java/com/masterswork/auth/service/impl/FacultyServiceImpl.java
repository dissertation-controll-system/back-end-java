package com.masterswork.auth.service.impl;

import com.masterswork.auth.api.dto.faculty.FacultyCreateDTO;
import com.masterswork.auth.api.dto.faculty.FacultyResponseDTO;
import com.masterswork.auth.api.dto.faculty.FacultyUpdateDTO;
import com.masterswork.auth.model.Faculty;
import com.masterswork.auth.service.mapper.FacultyMapper;
import com.masterswork.auth.repository.FacultyRepository;
import com.masterswork.auth.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Override
    public FacultyResponseDTO createFaculty(FacultyCreateDTO facultyResponseDTO) {
        Faculty faculty = facultyRepository.save(facultyMapper.createFrom(facultyResponseDTO));
        return facultyMapper.toDto(faculty);
    }

    @Override
    public FacultyResponseDTO updateFaculty(Long id, FacultyUpdateDTO facultyResponseDTO) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + id));
        facultyMapper.updateFrom(faculty, facultyResponseDTO);
        return facultyMapper.toDto(facultyRepository.save(faculty));
    }

    @Override
    public FacultyResponseDTO patchFaculty(Long id, FacultyUpdateDTO facultyResponseDTO) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + id));
        facultyMapper.patchFrom(faculty, facultyResponseDTO);
        return facultyMapper.toDto(facultyRepository.save(faculty));
    }

    @Override
    public List<FacultyResponseDTO> getAllFaculties() {
        List<Faculty> all = facultyRepository.findAll();
        return facultyMapper.toDto(all);
    }

    @Override
    public FacultyResponseDTO getFaculty(Long id) {
        return facultyRepository.findById(id)
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + id));
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}
