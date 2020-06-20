package com.extremecoder.mvel.rulesengine.rules.engine;

import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class ExpressionEvaluator implements Serializable {
    private String expression;
    private CompiledExpression compiledExpression;
    private boolean isCompiled;

    public ExpressionEvaluator(String expression){
        this.expression = expression;
        this.compile();
    }

    public <T extends ConcurrentHashMap<String,Object>> Object eval(T input) {
        try {
            if (isCompiled) {
              return  MVEL.executeExpression(compiledExpression, input);
            } else {
                compile();
               return MVEL.executeExpression(compiledExpression, input);
            }
        } catch (Exception exception) {
            //todo: handle exception and add logging.
            throw exception;
        }
    }

    public void compile(){
        try {
            ParserContext parserContext = new ParserContext();
            parserContext.addInput("input",RuleEngineContextInput.class);
            parserContext.setStrictTypeEnforcement(true);
            compiledExpression = new ExpressionCompiler(this.expression, parserContext).compile();
            isCompiled = true;
        }catch( Exception exception) {
            //todo: handle exception and add logging.
            throw exception;
        }
    }

}
