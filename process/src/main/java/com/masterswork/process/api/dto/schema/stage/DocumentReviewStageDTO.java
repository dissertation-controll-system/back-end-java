package com.masterswork.process.api.dto.schema.stage;

import lombok.Data;

@Data
public class DocumentReviewStageDTO extends AbstractStageDTO {

    private Long reviewerId;
    private Long userId;
    private String stageName;

}
