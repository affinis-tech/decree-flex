package com.extremecoder.mvel.rulesengine.rules.engine;

import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;
import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextOutput;
import com.extremecoder.mvel.rulesengine.rules.interfaces.RuleInferenceEngine;
import com.extremecoder.mvel.rulesengine.services.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RuleEngine implements RuleInferenceEngine {
    private static final String RULE_NAME_SPACE = "NAME_SPACE";
    private ConcurrentHashMap rulesByNameSpace = new ConcurrentHashMap();

    @Qualifier("knowledgeBaseService")
    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @Autowired
    private RuleInferenceEngine linearConflictSetResolutionStrategy;

    /**
     * @param context
     * @return
     */
    public RuleEngineContextOutput evaluate(RuleEngineContextInput context) {
        RuleEngineContextOutput output = null;
        List<Rule> rules = setup(context);
        if (rules != null && !rules.isEmpty()) {
               output = run(context,rules);
        } else {
            output = new RuleEngineContextOutput();
            output.put("Error", "No rules found for namespace");
        }
        return output;
    }

    private List<Rule> setup(Map<String, Object> context) {
        List<Rule> rules;
        Object name_space = context.get(RULE_NAME_SPACE).toString();
        if (name_space != null && (rulesByNameSpace.isEmpty() || !rulesByNameSpace.containsKey(name_space))) {
            rules = knowledgeBaseService
                    .findRulesByNameSpace(name_space.toString());
            if (rules != null && !rules.isEmpty()) {
                rulesByNameSpace.putIfAbsent(name_space, rules);
            }
        }
        rules = (List<Rule>)rulesByNameSpace.get(name_space);
        return rules;
    }

    @Override
    public RuleEngineContextOutput run(RuleEngineContextInput input, List<Rule> rules) {
        RuleEngineContextOutput output = linearConflictSetResolutionStrategy.run(input,rules);
        return output;
    }
}
