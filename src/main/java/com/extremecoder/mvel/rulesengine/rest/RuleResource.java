package com.extremecoder.mvel.rulesengine.rest;

import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;
import com.extremecoder.mvel.rulesengine.rules.engine.RuleAction;
import com.extremecoder.mvel.rulesengine.rules.engine.RuleCondition;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Builder
@Data
public class RuleResource implements Serializable {
    private int ruleId;
    @NotNull
    private String identifier;
    @NotNull
    private int version;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String status;
    @NotNull
    private String actionExpression;
    @NotNull
    private String conditionExpression;
    @NotNull
    private int sequence;
    @NotNull
    private String nameSpace;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private String createdBy;
    private String updatedBy;
}
