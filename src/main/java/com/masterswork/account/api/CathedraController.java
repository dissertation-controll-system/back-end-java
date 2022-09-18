package com.masterswork.account.api;

import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.cathedra.CathedraUpdateDTO;
import com.masterswork.account.service.CathedraService;
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

    @PutMapping("/{id}")
    public ResponseEntity<CathedraResponseDTO> updateCathedra(@PathVariable Long id, @Valid @RequestBody CathedraUpdateDTO body) {
        var updatedEntity = cathedraService.updateCathedra(id, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedEntity);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CathedraResponseDTO> patchCathedra(@PathVariable Long id, @RequestBody CathedraUpdateDTO body) {
        var patchedEntity = cathedraService.patchCathedra(id, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patchedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(patchedEntity);
    }

    @GetMapping
    public ResponseEntity<List<CathedraResponseDTO>> getAllCathedras() {
        return ResponseEntity.ok(cathedraService.getAllCathedras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CathedraResponseDTO> getCathedraById(@PathVariable Long id) {
        return ResponseEntity.ok(cathedraService.getCathedraById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCathedraById(@PathVariable Long id) {
        cathedraService.deleteCathedraById(id);
        return ResponseEntity.noContent().build();
    }
}
