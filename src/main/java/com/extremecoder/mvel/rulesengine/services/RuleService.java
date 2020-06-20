package com.extremecoder.mvel.rulesengine.services;

import com.extremecoder.mvel.rulesengine.RuleStatus;
import com.extremecoder.mvel.rulesengine.models.RulesEntity;
import com.extremecoder.mvel.rulesengine.rest.RuleResource;
import com.extremecoder.mvel.rulesengine.rules.engine.Rule;
import com.extremecoder.mvel.rulesengine.rules.engine.RuleAction;
import com.extremecoder.mvel.rulesengine.rules.engine.RuleCondition;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public abstract class RuleService<T,TId extends Serializable> implements KnowledgeBaseService<T,TId> {

    protected Rule getRule(RulesEntity rule) {
        return Rule
                .builder()
                .action(new RuleAction(rule.getAction()))
                .condition(new RuleCondition(rule.getCondition()))
                .identifier(rule.getIdentifier())
                .sequence(rule.getSequence())
                .nameSpace(rule.getNameSpace())
                .name(rule.getName())
                .status(rule.getStatus())
                .ruleId(rule.getRuleId())
                .version(rule.getVersion())
                .description(rule.getDescription())
                .createdBy(rule.getCreatedBy())
                .createdDate(rule.getCreatedDate())
                .updatedBy(rule.getUpdatedBy())
                .updatedDate(rule.getUpdatedDate())
                .build();
    }

    protected RuleResource getRuleResource(RulesEntity rule) {
        return getRule(rule)
                .ToResource();
    }

    protected RulesEntity fromRuleResourceToRuleEntity(RuleResource rule) {
        RulesEntity entity = new RulesEntity();
        entity.setAction(rule.getActionExpression());
        entity.setCondition(rule.getConditionExpression());
        return getRulesEntity(entity, rule.getIdentifier(), rule.getName(), rule.getNameSpace(), rule.getCreatedBy(), rule.getDescription(), rule.getUpdatedBy(), rule.getVersion(), rule.getSequence());
    }

    protected RulesEntity fromRuleToRuleEntity(Rule rule) {
        RulesEntity entity = new RulesEntity();
        entity.setAction(rule.getAction().getExpressionEvaluator().getExpression());
        entity.setCondition(rule.getCondition().getExpressionEvaluator().getExpression());
        return getRulesEntity(entity, rule.getIdentifier(), rule.getName(), rule.getNameSpace(), rule.getCreatedBy(), rule.getDescription(), rule.getUpdatedBy(), rule.getVersion(), rule.getSequence());
    }

    protected RulesEntity getRulesEntity(RulesEntity entity, String identifier, String name, String nameSpace, String createdBy, String description, String updatedBy, int version, int sequence) {
        entity.setIdentifier(identifier);
        entity.setName(name);
        entity.setNameSpace(nameSpace);
        entity.setCreatedBy(createdBy);
        entity.setDescription(description);
        entity.setUpdatedBy(updatedBy);
        /**
         *  Get and Convert DateTime in UTC to a TimeStamp.
         */
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        entity.setUpdatedDate(Timestamp.from(utc.toInstant()));
        entity.setVersion(version);
        entity.setSequence(sequence);
        entity.setStatus(RuleStatus.CREATED.name());
        return entity;
    }
}
