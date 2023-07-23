package com.masterswork.account.service;

import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.organization.OrganizationCreateDTO;
import com.masterswork.account.api.dto.organization.OrganizationResponseDTO;
import com.masterswork.account.api.dto.organization.OrganizationUpdateDTO;
import com.masterswork.account.model.AppUser;
import com.masterswork.account.model.OrganizationUnit;
import com.masterswork.account.model.enumeration.RoleName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface OrganizationUnitService {

    OrganizationResponseDTO createOrganization(OrganizationCreateDTO organizationCreateDTO);

    OrganizationUnit addUserToOrganizationWithRole(OrganizationUnit organizationUnit, AppUser user, RoleName roleName);

    OrganizationResponseDTO updateOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO);

    OrganizationResponseDTO patchOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO);

    Page<OrganizationResponseDTO> getAllOrganizations(Pageable pageable);

    OrganizationResponseDTO getOrganization(Long id);

    void deleteOrganization(Long id);

    OrganizationResponseDTO setOwner(Long organizationId, Long ownerId);

    OrganizationResponseDTO removeOwner(Long organizationId);

    Page<AppUserResponseDTO> getParticipantsByOrganizationId(Long id, Pageable pageable);

    Set<Long> getParticipantsForOrganizations(Set<Long> ids);

    OrganizationResponseDTO addParticipantToOrganization(Long organizationId, Long participantId);

    OrganizationResponseDTO removeParticipantFromOrganization(Long organizationId, Long participantId);
}
