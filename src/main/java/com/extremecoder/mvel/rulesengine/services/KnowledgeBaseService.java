package com.extremecoder.mvel.rulesengine.services;

import com.extremecoder.mvel.rulesengine.models.RulesEntity;
import com.extremecoder.mvel.rulesengine.rest.RuleResource;
import com.extremecoder.mvel.rulesengine.rules.engine.Rule;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface KnowledgeBaseService <T, TId extends Serializable> {
    /**
     *
     * @param nameSpace
     * @return
     */
    List<T> findRulesByNameSpace(String nameSpace);

    /**
     *
     * @param ruleId
     * @return
     */
    Optional<T> findByRuleId(TId ruleId);

    /**
     *
     * @param identifier
     * @return
     */
    Optional<T> findByIdentifier(String identifier);

    /**
     *
     * @param identifier
     * @param nameSpace
     * @return
     */
    Optional<T> findByIdentifierAndAndNameSpace(String identifier, String nameSpace);

    /**
     *
     * @param ruleId
     * @return
     */
    Optional<T> findByRuleId(Long ruleId);

    /**
     *
     * @param resource
     * @return
     */
     RuleResource saveAndFlush(RuleResource resource);

    /**
     *
     * @param rules
     * @return
     */
    List<RuleResource> save(List<RuleResource> rules);

}
