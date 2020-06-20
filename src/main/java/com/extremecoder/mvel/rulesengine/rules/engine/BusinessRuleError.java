package com.extremecoder.mvel.rulesengine.rules.engine;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BusinessRuleError {
    private String ruleName;
    private String description;
    private String message;
    private String status;
    private String identifier;
    private int version;
}
