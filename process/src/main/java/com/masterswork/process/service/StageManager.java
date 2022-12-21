package com.masterswork.process.service;

import com.masterswork.process.api.dto.process.AdditionalStageData;
import com.masterswork.process.model.relational.ProcessInstance;

import java.util.Map;
import java.util.Set;

public interface StageManager {

    void startProcess(String schemaId, Long ownerId, String ownerUsername, Set<Long> participantsIds, Map<Long, AdditionalStageData> stageData);

    void runNextStage(Long processInstanceId, String previousResult);

    void runStage(ProcessInstance processInstance);
}
