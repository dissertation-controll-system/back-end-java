package com.masterswork.organization.service;

import com.masterswork.organization.api.dto.organization.OrganizationCreateDTO;
import com.masterswork.organization.api.dto.organization.OrganizationResponseDTO;
import com.masterswork.organization.api.dto.organization.OrganizationUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

public interface OrganizationUnitService {

    OrganizationResponseDTO createOrganization(OrganizationCreateDTO organizationCreateDTO);

    OrganizationResponseDTO updateOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO);

    OrganizationResponseDTO patchOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO);

    Page<OrganizationResponseDTO> getAllOrganizations(Pageable pageable);

    OrganizationResponseDTO getOrganization(Long id);

    void deleteOrganization(Long id);

    OrganizationResponseDTO setOwner(Long organizationId, Long ownerId);

    OrganizationResponseDTO removeOwner(Long organizationId);

    Set<Long> getParticipantsIdsByOrganizationId(Long id);

    OrganizationResponseDTO addParticipantToOrganization(Long organizationId, Long participantId);

    OrganizationResponseDTO removeParticipantFromOrganization(Long organizationId, Long participantId);
}
