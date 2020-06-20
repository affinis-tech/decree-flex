package com.extremecoder.mvel.rulesengine.rules.interfaces;

import com.extremecoder.mvel.rulesengine.rules.engine.ActionResult;
import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;

import java.io.Serializable;

public interface Action extends Serializable {
    Object eval(RuleEngineContextInput input);
}
