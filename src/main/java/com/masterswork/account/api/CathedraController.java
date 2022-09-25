package com.masterswork.account.api;

import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.cathedra.CathedraUpdateDTO;
import com.masterswork.account.service.CathedraService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cathedras")
@RequiredArgsConstructor
public class CathedraController {

    private final CathedraService cathedraService;

    @Operation(summary = "Update cathedra by cathedraId")
    @PutMapping(path = "/{cathedraId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CathedraResponseDTO> updateCathedra(@PathVariable Long cathedraId, @Valid @RequestBody CathedraUpdateDTO body) {
        var updatedEntity = cathedraService.updateCathedra(cathedraId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{cathedraId}")
                .buildAndExpand(updatedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedEntity);
    }

    @Operation(summary = "Patch cathedra by cathedraId")
    @PatchMapping(path = "/{cathedraId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CathedraResponseDTO> patchCathedra(@PathVariable Long cathedraId, @RequestBody CathedraUpdateDTO body) {
        var patchedEntity = cathedraService.patchCathedra(cathedraId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{cathedraId}")
                .buildAndExpand(patchedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(patchedEntity);
    }

    @Operation(summary = "Assign cathedra to faculty")
    @PutMapping(path = "/{cathedraId}/faculties/{facultyId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CathedraResponseDTO> assignCathedraToFaculty(@PathVariable Long cathedraId, @PathVariable Long facultyId) {
        var updatedEntity = cathedraService.assignCathedraToFaculty(facultyId, cathedraId);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedEntity);
    }

    @Operation(summary = "Get all cathedras")
    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CathedraResponseDTO>> getAllCathedras() {
        return ResponseEntity.ok(cathedraService.getAllCathedras());
    }

    @Operation(summary = "Get cathedra by cathedraId")
    @GetMapping(path = "/{cathedraId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CathedraResponseDTO> getCathedraById(@PathVariable Long cathedraId) {
        return ResponseEntity.ok(cathedraService.getCathedraById(cathedraId));
    }

    @Operation(summary = "Delete cathedra by cathedraId")
    @DeleteMapping(path = "/{cathedraId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteCathedraById(@PathVariable Long cathedraId) {
        cathedraService.deleteCathedraById(cathedraId);
        return ResponseEntity.noContent().build();
    }
}
