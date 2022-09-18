package com.masterswork.account.api.dto.cathedra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CathedraUpdateDTO {

    @NotBlank
    private String name;

    @Min(1)
    private Long facultyId;
}
