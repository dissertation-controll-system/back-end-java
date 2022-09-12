package com.masterswork.auth.model;

import com.masterswork.auth.model.base.AuditedEntity;
import com.masterswork.auth.model.enumeration.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class AppUser extends AuditedEntity {

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String fathersName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PersonType type;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    @ManyToMany
    @JoinTable(name = "user_cathedra",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "cathedra_id"))
    private List<Cathedra> cathedras;

    public String getFullName() {
        return String.join(" ", lastName, firstName, fathersName);
    }
}
