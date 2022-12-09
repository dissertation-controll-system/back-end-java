package com.masterswork.process.model.nosql.process.stages;

import com.masterswork.process.model.nosql.process.base.AbstractStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendMailStage extends AbstractStage {

    private Long toId;
    private Long fromId;
    private Map<String, Object> parameters;
    private List<Long> attachmentsIds;

}
