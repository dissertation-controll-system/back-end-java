package com.masterswork.auth.model;

import com.masterswork.auth.model.base.AuditedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cathedra")
public class Cathedra extends AuditedEntity {

    @Id
    @Column(name = "cathedra_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "faculty_id", referencedColumnName = "faculty_id")
    private Faculty faculty;

    @ManyToMany(mappedBy = "cathedras")
    private List<AppUser> users;
}
