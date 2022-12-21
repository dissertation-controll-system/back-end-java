package com.masterswork.process.api.dto.schema.stage;


import com.masterswork.process.model.enumeration.StageType;
import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
public class StageDTO {

    private StageType stageType;
    
    private String name;

    private Map<String, Object> properties;

    private Map<String, Long> nextStages;

    public Long getNextStageId(String outcome) {
        return Optional.ofNullable(nextStages)
                .map(stage -> stage.get(outcome))
                .orElse(null);
    }
}
