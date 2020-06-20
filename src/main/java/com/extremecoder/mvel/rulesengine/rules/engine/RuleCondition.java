package com.extremecoder.mvel.rulesengine.rules.engine;

import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;
import com.extremecoder.mvel.rulesengine.rules.engine.ExpressionEvaluator;
import com.extremecoder.mvel.rulesengine.rules.interfaces.Condition;
import lombok.Data;


@Data
public class RuleCondition implements Condition {
    private ExpressionEvaluator expressionEvaluator;

    public RuleCondition(String expression){
        expressionEvaluator = new ExpressionEvaluator(expression);
    }

    @Override
    public boolean eval(RuleEngineContextInput input) {
        Object o  = expressionEvaluator.eval(input);
        if(o != null){
            return ((Boolean) o).booleanValue();
        }
        return false;
    }

    public void compile(){
        expressionEvaluator.compile();
    }
}