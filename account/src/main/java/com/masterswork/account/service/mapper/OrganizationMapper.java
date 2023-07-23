package com.masterswork.account.service.mapper;


import com.masterswork.account.api.dto.organization.OrganizationCreateDTO;
import com.masterswork.account.api.dto.organization.OrganizationResponseDTO;
import com.masterswork.account.api.dto.organization.OrganizationUpdateDTO;
import com.masterswork.account.model.OrganizationUnit;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participants", ignore = true)
    OrganizationUnit createFrom(OrganizationCreateDTO source);

    @Mapping(target = "id", ignore = true)
    void updateFrom(@MappingTarget OrganizationUnit target, OrganizationUpdateDTO source);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchFrom(@MappingTarget OrganizationUnit target, OrganizationUpdateDTO source);

    @Mapping(target = "ownerRef", expression = "java(mapOwnerReference(organizationUnit))")
    @Mapping(target = "headRef", expression = "java(mapHeadReference(organizationUnit))")
    OrganizationResponseDTO toDto(OrganizationUnit organizationUnit);

    List<OrganizationResponseDTO> toDto(Collection<OrganizationUnit> organizationUnits);

    default String mapOwnerReference(OrganizationUnit organizationUnit) {
        return Optional.ofNullable(organizationUnit.getOwner())
                .map(owner -> "/organization/" + owner.getId())
                .orElse(null);
    }

    default String mapHeadReference(OrganizationUnit organizationUnit) {
        return Optional.ofNullable(organizationUnit.getHead())
                .map(head -> "/app-users/" + head.getId())
                .orElse(null);
    }

}