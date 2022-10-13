package com.masterswork.storage.model;

import com.masterswork.storage.model.base.AuditedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stored_file")
public class StoredFile extends AuditedEntity {

    @Id
    @Column(name = "stored_file_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;

    @Column(name = "path", nullable = false, unique = true)
    private String path;

    @Column(name = "version", nullable = false)
    @ColumnDefault("1")
    @Builder.Default
    private Long version = 1L;

    @OneToMany(mappedBy = "file")
    private List<FileAccess> fileAccess;

    public StoredFile incrementVersion() {
        version++;
        return this;
    }
}
