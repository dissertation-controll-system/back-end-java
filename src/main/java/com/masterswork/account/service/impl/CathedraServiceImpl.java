package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.cathedra.CathedraCreateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.cathedra.CathedraUpdateDTO;
import com.masterswork.account.model.Cathedra;
import com.masterswork.account.model.Faculty;
import com.masterswork.account.repository.CathedraRepository;
import com.masterswork.account.repository.FacultyRepository;
import com.masterswork.account.service.CathedraService;
import com.masterswork.account.service.mapper.CathedraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CathedraServiceImpl implements CathedraService {

    private final CathedraMapper cathedraMapper;
    private final FacultyRepository facultyRepository;
    private final CathedraRepository cathedraRepository;

    @Override
    public CathedraResponseDTO createCathedraByFacultyId(Long facultyId, CathedraCreateDTO cathedraCreateDTO) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + facultyId));

        Cathedra newCathedra = cathedraMapper.createFrom(cathedraCreateDTO);
        newCathedra.setFaculty(faculty);

        return cathedraMapper.toDto(cathedraRepository.save(newCathedra));
    }

    @Override
    public List<CathedraResponseDTO> getAllCathedras() {
        List<Cathedra> all = cathedraRepository.findAll();

        return cathedraMapper.toDto(all);
    }

    @Override
    public List<CathedraResponseDTO> getAllCathedrasByAppUserId(Long appUserId) {
        return cathedraMapper.toDto(cathedraRepository.findAllByUsers_Id(appUserId));
    }

    @Override
    public List<CathedraResponseDTO> findAllCathedrasByFacultyId(Long facultyId) {
        List<Cathedra> allByFacultyId = cathedraRepository.findAllByFacultyId(facultyId);

        return cathedraMapper.toDto(allByFacultyId);
    }

    @Override
    public CathedraResponseDTO getCathedraById(Long cathedraId) {
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id: " + cathedraId));

        return cathedraMapper.toDto(cathedra);
    }

    @Override
    public CathedraResponseDTO getCathedraByFacultyIdAndCathedraId(Long facultyId, Long cathedraId) {
        Cathedra cathedra = cathedraRepository.findByFacultyIdAndId(facultyId, cathedraId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No cathedra with facultyId %d and cathedraId %d", facultyId, cathedraId)));

        return cathedraMapper.toDto(cathedra);
    }

    @Override
    public CathedraResponseDTO updateCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO) {
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id " + cathedraId));

        if (cathedraUpdateDTO.getFacultyId() != null) {
            updateFacultyById(cathedraUpdateDTO.getFacultyId(), cathedra);
        }

        cathedraMapper.updateFrom(cathedra, cathedraUpdateDTO);

        return cathedraMapper.toDto(cathedraRepository.save(cathedra));
    }

    @Override
    public CathedraResponseDTO assignCathedraToFaculty(Long facultyId, Long cathedraId) {
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with Id " + cathedraId));
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with Id " + facultyId));

        cathedra.setFaculty(faculty);

        return cathedraMapper.toDto(cathedraRepository.save(cathedra));
    }

    @Override
    public CathedraResponseDTO patchCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO) {
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id " + cathedraId));

        if (cathedraUpdateDTO.getFacultyId() != null) {
            updateFacultyById(cathedraUpdateDTO.getFacultyId(), cathedra);
        }
        cathedraMapper.patchFrom(cathedra, cathedraUpdateDTO);

        return cathedraMapper.toDto(cathedraRepository.save(cathedra));
    }

    @Override
    public void deleteCathedraById(Long cathedraId) {
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id " + cathedraId));

        cathedraRepository.delete(cathedra);
    }

    private void updateFacultyById(Long facultyId, Cathedra cathedra) {
        if (!ObjectUtils.nullSafeEquals(facultyId, cathedra.getFaculty().getId())) {
            Faculty faculty = facultyRepository.findById(facultyId)
                    .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + facultyId));

            cathedra.setFaculty(faculty);
        }
    }
}
