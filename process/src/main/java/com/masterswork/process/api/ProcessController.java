package com.masterswork.process.api;

import com.masterswork.process.api.dto.process.ProcessStartRequest;
import com.masterswork.process.repository.ProcessSchemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessSchemaRepository processSchemaRepository;

    @PostMapping("/{processId}/start")
    public ResponseEntity<?> startProcess(@PathVariable Long processId, @Valid @RequestBody ProcessStartRequest body) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{processId}/stages/{stageId}")
    public ResponseEntity<?> setStageResponse(@PathVariable Long processId, @PathVariable Long stageId, @Valid @RequestBody ProcessStartRequest body) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{processId}")
    public ResponseEntity<?> getProcess(@PathVariable Long processId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<?> getAllProcesses() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{processId}/stages/{stageId}")
    public ResponseEntity<?> getProcessStage(@PathVariable Long processId, @PathVariable Long stageId) {
        return ResponseEntity.ok(null);
    }

}
