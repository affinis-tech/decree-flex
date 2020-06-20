package com.extremecoder.mvel.rulesengine.rules.interfaces;


import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;

import java.io.Serializable;
import java.util.Optional;

public interface Condition extends Serializable {
    boolean eval(RuleEngineContextInput input);
}
