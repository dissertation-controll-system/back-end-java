package com.masterswork.account.model;

import com.masterswork.account.model.embeddable.AppUserOrganizationUnitId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserOrganizationUnit {

    @EmbeddedId
    private AppUserOrganizationUnitId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("organizationId")
    private OrganizationUnit organizationUnit;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    public static AppUserOrganizationUnit of(OrganizationUnit organizationUnit, AppUser appUser, Role role) {
        return AppUserOrganizationUnit.builder()
                .id(new AppUserOrganizationUnitId(appUser.getId(), organizationUnit.getId()))
                .organizationUnit(organizationUnit)
                .user(appUser)
                .role(role)
                .build();
    }
}
