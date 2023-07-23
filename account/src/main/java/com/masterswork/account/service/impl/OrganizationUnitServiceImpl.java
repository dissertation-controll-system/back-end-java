package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.organization.OrganizationCreateDTO;
import com.masterswork.account.api.dto.organization.OrganizationResponseDTO;
import com.masterswork.account.api.dto.organization.OrganizationUpdateDTO;
import com.masterswork.account.model.AppUser;
import com.masterswork.account.model.AppUserOrganizationUnit;
import com.masterswork.account.model.OrganizationUnit;
import com.masterswork.account.model.Role;
import com.masterswork.account.model.enumeration.RoleName;
import com.masterswork.account.repository.AppUserRepository;
import com.masterswork.account.repository.OrganizationUnitRepository;
import com.masterswork.account.repository.RoleRepository;
import com.masterswork.account.service.OrganizationUnitService;
import com.masterswork.account.service.mapper.AppUserMapper;
import com.masterswork.account.service.mapper.OrganizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationUnitServiceImpl implements OrganizationUnitService {

    private final OrganizationUnitRepository organizationUnitRepository;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final OrganizationMapper organizationMapper;
    private final AppUserMapper appUserMapper;

    @Override
    public OrganizationResponseDTO createOrganization(OrganizationCreateDTO organizationCreateDTO) {
        var newOrganization = organizationUnitRepository.save(organizationMapper.createFrom(organizationCreateDTO));

        var head = appUserRepository.findById(organizationCreateDTO.getHeadId())
                .orElseThrow(() -> new EntityNotFoundException("No User with id: " + organizationCreateDTO.getHeadId()));
        newOrganization.setHead(head);
        addUserToOrganizationWithRole(newOrganization, head, RoleName.UNIT_HEAD);

        var participants = appUserRepository.findAllByIdIn(organizationCreateDTO.getParticipants());
        participants.forEach(participant -> addUserToOrganizationWithRole(newOrganization, participant, RoleName.UNIT_PARTICIPANT));

        return organizationMapper.toDto(organizationUnitRepository.save(newOrganization));
    }

    @Override
    public OrganizationUnit addUserToOrganizationWithRole(OrganizationUnit organizationUnit, AppUser user, RoleName roleName) {
        Role role = roleRepository.findByName(roleName.getName());
        AppUserOrganizationUnit relation = AppUserOrganizationUnit.of(organizationUnit, user, role);
        organizationUnit.getParticipants().add(relation);
        return organizationUnit;
    }

    @Override
    public OrganizationResponseDTO updateOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO) {
        var organizationUnit = organizationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + id));
        organizationMapper.updateFrom(organizationUnit, organizationUpdateDTO);
        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public OrganizationResponseDTO patchOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO) {
        var organizationUnit = organizationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + id));
        organizationMapper.patchFrom(organizationUnit, organizationUpdateDTO);
        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public Page<OrganizationResponseDTO> getAllOrganizations(Pageable pageable) {
        return organizationUnitRepository.findAll(pageable).map(organizationMapper::toDto);
    }

    @Override
    public OrganizationResponseDTO getOrganization(Long id) {
        return organizationUnitRepository.findById(id)
                .map(organizationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No organization unit with id: " + id));
    }

    @Override
    public void deleteOrganization(Long id) {
        var organizationUnit = organizationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + id));
        organizationUnit.getSubs().forEach(OrganizationUnit::removeOwner);
        organizationUnitRepository.deleteById(id);
    }

    @Override
    public OrganizationResponseDTO setOwner(Long organizationId, Long ownerId) {
        var organizationUnit = organizationUnitRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + organizationId));
        var owner = organizationUnitRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + ownerId));

        organizationUnit.setOwner(owner);

        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public OrganizationResponseDTO removeOwner(Long organizationId) {
        var organizationUnit = organizationUnitRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + organizationId));

        organizationUnit.removeOwner();

        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public Page<AppUserResponseDTO> getParticipantsByOrganizationId(Long id, Pageable pageable) {
        return appUserRepository.findAppUserByOrganizationUnits_OrganizationUnit_Id(id, pageable)
                .map(appUserMapper::toDto);
    }

    @Override
    public Set<Long> getParticipantsForOrganizations(Set<Long> ids) {
        return appUserRepository.findAppUserByOrganizationUnits_Id_OrganizationIdIn(ids).stream()
                .map(AppUser::getId)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public OrganizationResponseDTO addParticipantToOrganization(Long organizationId, Long participantId) {
        var organizationUnit = organizationUnitRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + organizationId));
        var appUser = appUserRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("No AppUser with id: " + participantId));
        var participantRole = roleRepository.findByName(RoleName.UNIT_PARTICIPANT.getName());

        var relation = AppUserOrganizationUnit.of(organizationUnit, appUser, participantRole);
        organizationUnit.getParticipants().add(relation);

        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public OrganizationResponseDTO removeParticipantFromOrganization(Long organizationId, Long participantId) {
        var organizationUnit = organizationUnitRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + organizationId));
        organizationUnit.getParticipants().remove(participantId);
        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }
}
