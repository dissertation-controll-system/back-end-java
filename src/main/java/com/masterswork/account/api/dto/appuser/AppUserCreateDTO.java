package com.masterswork.account.api.dto.appuser;

import com.masterswork.account.model.enumeration.PersonType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class AppUserCreateDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String fathersName;

    @NotNull
    private PersonType type;

    private Long accountId;

    private Set<Long> cathedrasIds;
}
