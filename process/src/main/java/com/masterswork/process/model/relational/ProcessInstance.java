package com.masterswork.process.model.relational;

import com.masterswork.process.model.relational.base.AuditedEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "process_instance")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProcessInstance extends AuditedEntity {

    @Id
    @Column(name = "process_instance_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schema_id")
    private String schemaId;

    @Column(name = "current_stage")
    private Long currentStage;

    @Column(name = "is_finished", columnDefinition = "boolean default false")
    private boolean isFinished = false;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "owner_username")
    private String ownerUsername;

    @Column(name = "subordinate_id")
    private Long subordinateId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processInstance")
    private Set<DocumentReview> reviews;

    public static ProcessInstance of(String schemaId, Long currentStage, Long ownerId,  String ownerUsername, Long subordinateId) {
        return ProcessInstance.builder()
                .schemaId(schemaId)
                .currentStage(currentStage)
                .ownerId(ownerId)
                .ownerUsername(ownerUsername)
                .subordinateId(subordinateId)
                .build();
    }

    public ProcessInstance addReview(DocumentReview review) {
        this.reviews.add(review);
        review.setProcessInstance(this);
        return this;
    }
}
