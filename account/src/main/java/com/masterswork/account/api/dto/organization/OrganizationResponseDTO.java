package com.masterswork.account.api.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponseDTO {

    private Long id;

    private String name;

    @NotEmpty
    private String headRef;

    private Set<OrganizationResponseDTO> subs;

    private String ownerRef;


}
