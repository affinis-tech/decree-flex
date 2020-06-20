package com.extremecoder.mvel.rulesengine.rules.interfaces;

import com.extremecoder.mvel.rulesengine.rules.engine.Rule;
import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;
import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextOutput;

import java.util.List;

public interface RuleInferenceEngine {
    public RuleEngineContextOutput run(RuleEngineContextInput input, List<Rule> rules);
}
