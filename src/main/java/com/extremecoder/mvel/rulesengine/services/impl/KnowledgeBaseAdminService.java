package com.extremecoder.mvel.rulesengine.services.impl;

import com.extremecoder.mvel.rulesengine.models.RulesEntity;
import com.extremecoder.mvel.rulesengine.repositories.RulesRepository;
import com.extremecoder.mvel.rulesengine.rest.RuleResource;
import com.extremecoder.mvel.rulesengine.services.KnowledgeBaseService;
import com.extremecoder.mvel.rulesengine.services.RuleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = {"adminRulesCache"})
@Service("knowledgeBaseAdminService")
public class KnowledgeBaseAdminService extends RuleService<RuleResource, Long>{

    private RulesRepository rulesRepository;

    public KnowledgeBaseAdminService(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    @Cacheable(key = "#nameSpace")
    @Transactional(readOnly = true)
    @Override
    public List<RuleResource> findRulesByNameSpace(String nameSpace) {
        return rulesRepository.findRulesEntitiesByNameSpace(nameSpace)
                .stream()
                .map((rule) -> getRule(rule).ToResource())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<RuleResource> findByRuleId(Long ruleId) {
        rulesRepository.findByRuleId(ruleId)
                .ifPresent(rule -> getRule(rule));
        return Optional.empty();
    }

    /**
     * @param identifier
     * @return
     */
    @Override
    public Optional<RuleResource> findByIdentifier(String identifier) {
        return Optional.empty();
    }

    /**
     * @param identifier
     * @param nameSpace
     * @return
     */
    @Override
    public Optional<RuleResource> findByIdentifierAndAndNameSpace(String identifier, String nameSpace) {
        return Optional.empty();
    }

    /**
     * @param rule
     * @return
     */
    @Transactional
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
    @Transactional
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
}
