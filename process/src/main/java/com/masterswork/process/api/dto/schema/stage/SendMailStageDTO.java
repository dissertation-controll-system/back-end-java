package com.masterswork.process.api.dto.schema.stage;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SendMailStageDTO extends AbstractStageDTO {

    private Long toId;
    private Long fromId;
    private Map<String, Object> parameters;
    private List<Long> attachmentsIds;
}
