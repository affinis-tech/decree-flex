package com.extremecoder.mvel.rulesengine.rules.engine;

import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;
import com.extremecoder.mvel.rulesengine.rules.interfaces.Action;
import lombok.Data;

@Data
public class RuleAction implements Action {

    private ExpressionEvaluator expressionEvaluator;

    public RuleAction(String expression){
        expressionEvaluator = new ExpressionEvaluator(expression);
    }

    @Override
    public Object eval(RuleEngineContextInput input) {
        Object result = expressionEvaluator.eval(input);
        if (result != null) {
            return result;
        }
        return null;
    }

    public void compile(){
        expressionEvaluator.compile();
    }
}
