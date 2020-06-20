package com.extremecoder.mvel.rulesengine.services.impl;

import com.extremecoder.mvel.rulesengine.RuleStatus;
import com.extremecoder.mvel.rulesengine.models.RulesEntity;
import com.extremecoder.mvel.rulesengine.repositories.RulesRepository;
import com.extremecoder.mvel.rulesengine.rest.RuleResource;
import com.extremecoder.mvel.rulesengine.rules.engine.Rule;
import com.extremecoder.mvel.rulesengine.rules.engine.RuleAction;
import com.extremecoder.mvel.rulesengine.rules.engine.RuleCondition;
import com.extremecoder.mvel.rulesengine.rules.engine.RuleEngine;
import com.extremecoder.mvel.rulesengine.services.KnowledgeBaseService;
import com.extremecoder.mvel.rulesengine.services.RuleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * KnowledgeBase Class to read Rules from DB
 */
@CacheConfig(cacheNames = "rules")
@Service("knowledgeBaseService")
public class KnowledgeBaseServiceImpl extends RuleService<Rule,Long> {

    private RulesRepository rulesRepository;

    public KnowledgeBaseServiceImpl(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    @Cacheable(value = "evalCache", key = "#nameSpace")
    @Transactional(readOnly = true)
    @Override
    public List<Rule> findRulesByNameSpace(String nameSpace) {
        return rulesRepository.findRulesEntitiesByNameSpace(nameSpace)
                .stream()
                .map((rule) -> getRule(rule))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Rule> findByRuleId(Long ruleId) {
        rulesRepository.findByRuleId(ruleId)
                .ifPresent(rule -> getRule(rule));
        return Optional.empty();
    }

    /**
     * @param rule
     * @return
     */
    @Override
    public RuleResource saveAndFlush(RuleResource rule) {
        RulesEntity rulesEntity = fromRuleResourceToRuleEntity(rule);
        Optional<RulesEntity> result = Optional.ofNullable(rulesRepository.saveAndFlush(rulesEntity));
        if (result.isPresent()) {
            return getRuleResource(result.get());
        }
        return null;
    }

    /**
     * @param rules
     * @return
     */
    @Override
    public List<RuleResource> save(List<RuleResource> rules) {
        List<RulesEntity> _rules = rules
                .stream()
                .map(r -> fromRuleResourceToRuleEntity(r))
                .collect(Collectors.toList());
        List<RulesEntity> result = rulesRepository.save(_rules);
        return result.stream().map(rulesEntity -> getRuleResource(rulesEntity))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Rule> findByIdentifier(String identifier) {
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Rule> findByIdentifierAndAndNameSpace(String identifier, String nameSpace) {
        rulesRepository.findByIdentifierAndAndNameSpace(identifier, nameSpace)
                .ifPresent(rule -> getRule(rule));
        return Optional.empty();
    }

}
