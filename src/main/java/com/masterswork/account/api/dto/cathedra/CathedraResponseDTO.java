package com.masterswork.account.api.dto.cathedra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CathedraResponseDTO {

    private Long id;

    private String name;

    private String facultyRef;

}
