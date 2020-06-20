package com.extremecoder.mvel.rulesengine.rules.engine;

import com.extremecoder.mvel.rulesengine.rest.RuleResource;
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
public class Rule implements Serializable {
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
    private RuleAction action;
    private RuleCondition condition;
    @NotNull
    private int sequence;
    @NotNull
    private String nameSpace;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private String createdBy;
    private String updatedBy;

    public boolean isMatch(RuleEngineContextInput input){
        return condition.eval(input);
    }

    public RuleResource ToResource(){
        return RuleResource
                .builder()
                .actionExpression(this.action.getExpressionEvaluator().getExpression())
                .conditionExpression( this.condition.getExpressionEvaluator().getExpression())
                .identifier(this.identifier)
                .sequence(this.sequence)
                .nameSpace(this.nameSpace)
                .name(this.name)
                .status(this.status)
                .ruleId(this.ruleId)
                .version(this.getVersion())
                .description(this.description)
                .createdBy(this.createdBy)
                .createdDate(this.createdDate)
                .updatedBy(this.updatedBy)
                .updatedDate(this.updatedDate)
                .build();
    }
}
