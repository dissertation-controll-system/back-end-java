package com.masterswork.storage.model;

import com.masterswork.storage.model.base.AuditedEntity;
import com.masterswork.storage.model.enumeration.FileAccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file_access")
public class FileAccess extends AuditedEntity {

    @Id
    @Column(name = "file_access_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "access_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private FileAccessLevel accessLevel;

    @ManyToOne
    @JoinColumn(name = "stored_file_id", nullable = false)
    private StoredFile file;
}
