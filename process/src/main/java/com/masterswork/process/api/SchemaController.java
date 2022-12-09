package com.masterswork.process.api;

import com.masterswork.process.api.dto.schema.SchemaCreateDTO;
import com.masterswork.process.model.nosql.ProcessSchema;
import com.masterswork.process.repository.ProcessSchemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/schema")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class SchemaController {

    private final ProcessSchemaRepository processSchemaRepository;

    @PostMapping
    public ResponseEntity<ProcessSchema> startProcess(@Valid @RequestBody SchemaCreateDTO schemaCreateDTO) {
        ProcessSchema processSchema = processSchemaRepository.save(ProcessSchema.of(schemaCreateDTO));
        return ResponseEntity.ok(processSchema);
    }
}
