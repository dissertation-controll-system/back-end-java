package com.masterswork.process.api.dto.schema.stage;


import com.masterswork.process.model.nosql.process.enumaeration.StageType;
import lombok.Data;

import java.util.Map;

@Data
public abstract class AbstractStageDTO {

    protected StageType stageType;

    protected Map<String, Long> nextStages;
}
