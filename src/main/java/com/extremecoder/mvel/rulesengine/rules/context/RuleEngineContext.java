package com.extremecoder.mvel.rulesengine.rules.context;

import lombok.Data;

@Data
public class RuleEngineContext <I extends RuleEngineContextInput, O extends RuleEngineContextOutput> {
    private I input;
    private O output;

    private void addInput(String key, String value){
        input.putIfAbsent(key, value);
    }

    private void addOutput(String key, String value){
        output.putIfAbsent(key, value);
    }
}
