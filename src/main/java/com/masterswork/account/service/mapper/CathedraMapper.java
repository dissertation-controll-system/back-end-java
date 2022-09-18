package com.masterswork.account.service.mapper;

import com.masterswork.account.api.dto.cathedra.CathedraCreateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.cathedra.CathedraUpdateDTO;
import com.masterswork.account.model.Cathedra;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CathedraMapper {

    @Mapping(target = "facultyRef", expression = "java(mapFacultyReference(cathedra))")
    CathedraResponseDTO toDto(Cathedra cathedra);

    List<CathedraResponseDTO> toDto(Collection<Cathedra> all);

    @Mapping(target = "id", ignore = true)
    Cathedra createFrom(CathedraCreateDTO cathedraResponseDTO);

    @Mapping(target = "id", ignore = true)
    void updateFrom(@MappingTarget Cathedra target, CathedraUpdateDTO source);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchFrom(@MappingTarget Cathedra target, CathedraUpdateDTO source);

    default String mapFacultyReference(Cathedra cathedra) {
        return "/faculty/" + cathedra.getFaculty().getId();
    }
}