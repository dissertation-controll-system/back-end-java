package com.masterswork.account.api;

import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.organization.OrganizationCreateDTO;
import com.masterswork.account.api.dto.organization.OrganizationResponseDTO;
import com.masterswork.account.api.dto.organization.OrganizationUpdateDTO;
import com.masterswork.account.service.OrganizationUnitService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationUnitService organizationUnitService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrganizationResponseDTO> createOrganization(@Valid @RequestBody OrganizationCreateDTO organizationCreateDTO) {
        var createdOrganization = organizationUnitService.createOrganization(organizationCreateDTO);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrganization.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdOrganization);
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<OrganizationResponseDTO>> getAllOrganizations(
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(organizationUnitService.getAllOrganizations(pageable));
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/{organizationId}", produces = "application/json")
    public ResponseEntity<OrganizationResponseDTO> getOrganization(@PathVariable Long organizationId) {
        return ResponseEntity.ok(organizationUnitService.getOrganization(organizationId));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{organizationId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrganizationResponseDTO> updateOrganization(@PathVariable Long organizationId, @Valid @RequestBody OrganizationUpdateDTO body) {
        var updatedEntity = organizationUnitService.updateOrganization(organizationId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedEntity);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/{organizationId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrganizationResponseDTO> patchOrganization(@PathVariable Long organizationId, @RequestBody OrganizationUpdateDTO body) {
        var patchedEntity = organizationUnitService.patchOrganization(organizationId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(patchedEntity);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{organizationId}", produces = "application/json")
    public ResponseEntity<?> deleteOrganization(@PathVariable Long organizationId) {
        organizationUnitService.deleteOrganization(organizationId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{organizationId}/owner/{ownerId}", produces = "application/json")
    public ResponseEntity<OrganizationResponseDTO> setOwner(@PathVariable Long organizationId, @PathVariable Long ownerId) {
        return ResponseEntity.ok(organizationUnitService.setOwner(organizationId, ownerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{organizationId}/owner/", produces = "application/json")
    public ResponseEntity<OrganizationResponseDTO> removeOwner(@PathVariable Long organizationId) {
        return ResponseEntity.ok(organizationUnitService.removeOwner(organizationId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/{organizationId}/participants", produces = "application/json")
    public ResponseEntity<Page<AppUserResponseDTO>> getParticipants(
            @PathVariable Long organizationId, @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(organizationUnitService.getParticipantsByOrganizationId(organizationId, pageable));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/participants", produces = "application/json")
    public ResponseEntity<Set<Long>> getParticipantsForOrganizations(@RequestParam Set<Long> organizationId) {
        return ResponseEntity.ok(organizationUnitService.getParticipantsForOrganizations(organizationId));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/{organizationId}/participants/{participantId}", produces = "application/json")
    public ResponseEntity<OrganizationResponseDTO> addParticipantToOrganization(
            @PathVariable Long organizationId, @PathVariable Long participantId) {
        return ResponseEntity.ok(organizationUnitService.addParticipantToOrganization(organizationId, participantId));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(path = "/{organizationId}/participants/{participantId}", produces = "application/json")
    public ResponseEntity<OrganizationResponseDTO> removeParticipantFromOrganization(
            @PathVariable Long organizationId, @PathVariable Long participantId) {
        return ResponseEntity.ok(organizationUnitService.removeParticipantFromOrganization(organizationId, participantId));
    }

}
