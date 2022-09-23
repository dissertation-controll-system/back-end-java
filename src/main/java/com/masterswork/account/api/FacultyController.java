package com.masterswork.account.api;

import com.masterswork.account.api.dto.cathedra.CathedraCreateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.faculty.FacultyCreateDTO;
import com.masterswork.account.api.dto.faculty.FacultyResponseDTO;
import com.masterswork.account.api.dto.faculty.FacultyUpdateDTO;
import com.masterswork.account.service.CathedraService;
import com.masterswork.account.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/faculties")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;
    private final CathedraService cathedraService;

    @PostMapping
    public ResponseEntity<FacultyResponseDTO> createFaculty(@Valid @RequestBody FacultyCreateDTO body) {
        var newEntity = facultyService.createFaculty(body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEntity.getId())
                .toUri();
        return ResponseEntity.created(location).body(newEntity);
    }

    @GetMapping
    public ResponseEntity<List<FacultyResponseDTO>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("/{facultyId}")
    public ResponseEntity<FacultyResponseDTO> getFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(facultyService.getFaculty(facultyId));
    }

    @PutMapping("/{facultyId}")
    public ResponseEntity<FacultyResponseDTO> updateFaculty(@PathVariable Long facultyId, @Valid @RequestBody FacultyUpdateDTO body) {
        var updatedEntity = facultyService.updateFaculty(facultyId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{facultyId}")
                .buildAndExpand(updatedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedEntity);
    }

    @PatchMapping("/{facultyId}")
    public ResponseEntity<FacultyResponseDTO> patchFaculty(@PathVariable Long facultyId, @RequestBody FacultyUpdateDTO body) {
        var patchedEntity = facultyService.patchFaculty(facultyId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{facultyId}")
                .buildAndExpand(patchedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(patchedEntity);
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{facultyId}/cathedras")
    public ResponseEntity<CathedraResponseDTO> addCathedra(@PathVariable Long facultyId, @Valid @RequestBody CathedraCreateDTO body) {
        var newEntity = cathedraService.createCathedraByFacultyId(facultyId, body);
        var location = ServletUriComponentsBuilder.fromUriString("/cathedras")
                .path("/{id}")
                .buildAndExpand(newEntity.getId())
                .toUri();
        return ResponseEntity.created(location).body(newEntity);
    }

    @GetMapping("/{facultyId}/cathedras")
    public ResponseEntity<List<CathedraResponseDTO>> getCathedrasByFacultyId(@PathVariable Long facultyId) {
        return ResponseEntity.ok(cathedraService.findAllCathedrasByFacultyId(facultyId));
    }

    @GetMapping("/{facultyId}/cathedras/{cathedraId}")
    public ResponseEntity<CathedraResponseDTO> getCathedraByFacultyIdAndCathedraId(
            @PathVariable Long facultyId, @PathVariable Long cathedraId) {
        return ResponseEntity.ok(cathedraService.getCathedraByFacultyIdAndCathedraId(facultyId, cathedraId));
    }

    @PutMapping("/{facultyId}/cathedras/{cathedraId}")
    public ResponseEntity<CathedraResponseDTO> assignCathedraToFaculty(@PathVariable Long facultyId, @PathVariable Long cathedraId) {
        var updatedEntity = cathedraService.assignCathedraToFaculty(facultyId, cathedraId);
        var location = ServletUriComponentsBuilder.fromUriString("/cathedras")
                .path("/{id}")
                .buildAndExpand(updatedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedEntity);
    }

}
