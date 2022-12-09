package com.masterswork.process.model.nosql;

import com.masterswork.process.api.dto.schema.SchemaCreateDTO;
import com.masterswork.process.model.nosql.process.base.AbstractStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessSchema {

    @Id
    private String id;

    private Map<Long, AbstractStage> stages;

    public static ProcessSchema of(SchemaCreateDTO schemaCreateDTO) {
        return ProcessSchema.builder().build();
    }

}
