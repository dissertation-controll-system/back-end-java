package com.masterswork.account.model;

import com.masterswork.account.model.base.AuditedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization")
public class OrganizationUnit extends AuditedEntity {

    @Id
    @Column(name = "organization_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OrganizationUnit owner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "head_id")
    private AppUser head;

    @Builder.Default
    @OneToMany(mappedBy = "organizationUnit", cascade = CascadeType.ALL)
    private Set<AppUserOrganizationUnit> participants = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<OrganizationUnit> subs = new HashSet<>();

    public OrganizationUnit setOwner(OrganizationUnit owner) {
        if (Objects.equals(this.getId(), owner.getId())) {
            throw new IllegalArgumentException("Organization can not bet it's own owner");
        }
        if (this.owner != null) {
            this.owner.getSubs().remove(this);
        }
        this.owner = owner;
        owner.getSubs().add(this);
        return this;
    }

    public OrganizationUnit removeOwner() {
        if (this.owner != null) {
            this.owner.getSubs().remove(this);
            this.owner = null;
        }
        return this;
    }

    public OrganizationUnit setHead(AppUser head) {
        this.head = head;
        return this;
    }
}
