package com.extremecoder.mvel.rulesengine.rules.engine;

import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextOutput;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ActionResult {
    private Object result;
    private BusinessRuleError businessRuleError;
}
