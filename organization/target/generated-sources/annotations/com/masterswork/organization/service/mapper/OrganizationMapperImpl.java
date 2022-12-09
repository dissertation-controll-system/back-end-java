package com.masterswork.organization.service.mapper;

import com.masterswork.organization.api.dto.process.OrganizationCreateDTO;
import com.masterswork.organization.api.dto.process.OrganizationResponseDTO;
import com.masterswork.organization.api.dto.process.OrganizationUpdateDTO;
import com.masterswork.organization.model.OrganizationUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-04T14:09:35-0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.17 (Amazon.com Inc.)"
)
@Component
public class OrganizationMapperImpl implements OrganizationMapper {

    @Override
    public OrganizationUnit createFrom(OrganizationCreateDTO source) {
        if ( source == null ) {
            return null;
        }

        OrganizationUnit.OrganizationUnitBuilder organizationUnit = OrganizationUnit.builder();

        organizationUnit.name( source.getName() );
        organizationUnit.headId( source.getHeadId() );
        Set<Long> set = source.getParticipants();
        if ( set != null ) {
            organizationUnit.participants( new LinkedHashSet<Long>( set ) );
        }

        return organizationUnit.build();
    }

    @Override
    public void updateFrom(OrganizationUnit target, OrganizationUpdateDTO source) {
        if ( source == null ) {
            return;
        }

        target.setHeadId( source.getHeadId() );
        target.setName( source.getName() );
    }

    @Override
    public void patchFrom(OrganizationUnit target, OrganizationUpdateDTO source) {
        if ( source == null ) {
            return;
        }

        if ( source.getHeadId() != null ) {
            target.setHeadId( source.getHeadId() );
        }
        if ( source.getName() != null ) {
            target.setName( source.getName() );
        }
    }

    @Override
    public OrganizationResponseDTO toDto(OrganizationUnit organizationUnit) {
        if ( organizationUnit == null ) {
            return null;
        }

        OrganizationResponseDTO.OrganizationResponseDTOBuilder organizationResponseDTO = OrganizationResponseDTO.builder();

        Set<Long> set = organizationUnit.getParticipants();
        if ( set != null ) {
            organizationResponseDTO.participantsIds( new LinkedHashSet<Long>( set ) );
        }
        organizationResponseDTO.id( organizationUnit.getId() );
        organizationResponseDTO.name( organizationUnit.getName() );
        organizationResponseDTO.headId( organizationUnit.getHeadId() );

        organizationResponseDTO.subsId( mapSubsIds(organizationUnit) );
        organizationResponseDTO.ownerRef( mapOwnerReference(organizationUnit) );

        return organizationResponseDTO.build();
    }

    @Override
    public List<OrganizationResponseDTO> toDto(Collection<OrganizationUnit> organizationUnits) {
        if ( organizationUnits == null ) {
            return null;
        }

        List<OrganizationResponseDTO> list = new ArrayList<OrganizationResponseDTO>( organizationUnits.size() );
        for ( OrganizationUnit organizationUnit : organizationUnits ) {
            list.add( toDto( organizationUnit ) );
        }

        return list;
    }
}
