package com.masterswork.process.api.dto.schema;

import com.masterswork.process.api.dto.schema.stage.AbstractStageDTO;
import lombok.Data;

import java.util.Map;

@Data
public class SchemaCreateDTO {

    private Map<Long, AbstractStageDTO> stages;
}
