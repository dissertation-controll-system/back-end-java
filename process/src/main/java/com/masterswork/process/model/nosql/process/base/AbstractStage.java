package com.masterswork.process.model.nosql.process.base;

import java.util.Map;
import java.util.Optional;


public abstract class AbstractStage {

    protected Map<String, Long> nextStages;

    public Long getNext(String argument) {
        Object key = Optional.ofNullable(argument).orElse("DEFAULT");
        return nextStages.get(key);
    }
}
