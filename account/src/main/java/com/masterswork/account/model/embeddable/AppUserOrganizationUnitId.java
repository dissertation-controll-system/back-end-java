package com.masterswork.account.model.embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserOrganizationUnitId implements Serializable {

    private static final long serialVersionUID = -734950702834442442L;

    private Long userId;
    private Long organizationId;

}
