package com.extremecoder.mvel.rulesengine.rules.engine;

import antlr.StringUtils;
import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;
import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextOutput;
import com.extremecoder.mvel.rulesengine.rules.interfaces.Action;
import com.extremecoder.mvel.rulesengine.rules.interfaces.RuleInferenceEngine;
import com.sun.javafx.binding.StringFormatter;
import javafx.collections.transformation.SortedList;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

@Component
public class LinearConflictSetResolutionStrategy implements RuleInferenceEngine {
    @Override
    public RuleEngineContextOutput run(RuleEngineContextInput input, List<Rule> rules) {
        RuleEngineContextOutput ruleEngineContextOutput = new RuleEngineContextOutput();
        LinkedList<Rule> conflictSet = new LinkedList<>();
        for (Rule r : rules) {
            BusinessRuleError businessRuleError = null;
            if (r.isMatch(input)) {
                conflictSet.add(r);
            } else {
                businessRuleError = BusinessRuleError
                        .builder()
                        .message(String.format("Rule %s, with description %s was not a match.", r.getName(), r.getDescription()))
                        .description(r.getDescription())
                        .ruleName(r.getName())
                        .version(r.getVersion())
                        .status("False")
                        .identifier(r.getIdentifier())
                        .build();
            }
            if (businessRuleError != null) {
                ruleEngineContextOutput.put(businessRuleError.getIdentifier(), businessRuleError);
            }
        }
        if (conflictSet.size() == 0) {
            return ruleEngineContextOutput;
        } else {
            return runConflictSetAction(conflictSet, input, ruleEngineContextOutput);
        }
    }

    private RuleEngineContextOutput runConflictSetAction(LinkedList<Rule> rules, RuleEngineContextInput input, RuleEngineContextOutput output) {
        for (Rule r : rules) {
            Object result = r.getAction().eval(input);
            ActionResult actionResult = ActionResult.builder().build();
            if (result != null) {
                actionResult.setResult(result);
            } else {
                BusinessRuleError be = BusinessRuleError
                        .builder()
                        .message(String.format("Rule %s, with description %s action execution failed", r.getName(), r.getDescription()))
                        .description(r.getDescription())
                        .ruleName(r.getName())
                        .version(r.getVersion())
                        .status("False")
                        .identifier(r.getIdentifier())
                        .build();
                actionResult.setBusinessRuleError(be);
            }
            output.put(r.getName(), actionResult);
        }
        return output;
    }
}
