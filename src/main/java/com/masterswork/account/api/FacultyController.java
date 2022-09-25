package com.masterswork.account.api;

import com.masterswork.account.api.dto.cathedra.CathedraCreateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.faculty.FacultyCreateDTO;
import com.masterswork.account.api.dto.faculty.FacultyResponseDTO;
import com.masterswork.account.api.dto.faculty.FacultyUpdateDTO;
import com.masterswork.account.service.CathedraService;
import com.masterswork.account.service.FacultyService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create new faculty")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<FacultyResponseDTO> createFaculty(@Valid @RequestBody FacultyCreateDTO body) {
        var newEntity = facultyService.createFaculty(body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEntity.getId())
                .toUri();
        return ResponseEntity.created(location).body(newEntity);
    }

    @Operation(summary = "Get all faculties")
    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<FacultyResponseDTO>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @Operation(summary = "Get faculty by facultyId")
    @GetMapping(path = "/{facultyId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<FacultyResponseDTO> getFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(facultyService.getFaculty(facultyId));
    }

    @Operation(summary = "Update faculty by facultyId")
    @PutMapping(path = "/{facultyId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<FacultyResponseDTO> updateFaculty(@PathVariable Long facultyId, @Valid @RequestBody FacultyUpdateDTO body) {
        var updatedEntity = facultyService.updateFaculty(facultyId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{facultyId}")
                .buildAndExpand(updatedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedEntity);
    }

    @Operation(summary = "Patch faculty by facultyId")
    @PatchMapping(path = "/{facultyId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<FacultyResponseDTO> patchFaculty(@PathVariable Long facultyId, @RequestBody FacultyUpdateDTO body) {
        var patchedEntity = facultyService.patchFaculty(facultyId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{facultyId}")
                .buildAndExpand(patchedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(patchedEntity);
    }

    @Operation(summary = "Delete faculty by facultyId")
    @DeleteMapping(path = "/{facultyId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new cathedra and assign to faculty by facultyId")
    @PostMapping(path = "/{facultyId}/cathedras", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CathedraResponseDTO> addCathedra(@PathVariable Long facultyId, @Valid @RequestBody CathedraCreateDTO body) {
        var newEntity = cathedraService.createCathedraByFacultyId(facultyId, body);
        var location = ServletUriComponentsBuilder.fromUriString("/cathedras")
                .path("/{id}")
                .buildAndExpand(newEntity.getId())
                .toUri();
        return ResponseEntity.created(location).body(newEntity);
    }

    @Operation(summary = "Get all cathedras assigned to faculty by facultyId")
    @GetMapping(path = "/{facultyId}/cathedras", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CathedraResponseDTO>> getCathedrasByFacultyId(@PathVariable Long facultyId) {
        return ResponseEntity.ok(cathedraService.findAllCathedrasByFacultyId(facultyId));
    }

    @Operation(summary = "Get cathedra by facultyId and cathedraId ")
    @GetMapping(path = "/{facultyId}/cathedras/{cathedraId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CathedraResponseDTO> getCathedraByFacultyIdAndCathedraId(
            @PathVariable Long facultyId, @PathVariable Long cathedraId) {
        return ResponseEntity.ok(cathedraService.getCathedraByFacultyIdAndCathedraId(facultyId, cathedraId));
    }

}
