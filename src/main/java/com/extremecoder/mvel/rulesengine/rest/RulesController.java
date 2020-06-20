package com.extremecoder.mvel.rulesengine.rest;

import com.extremecoder.mvel.rulesengine.rules.engine.Rule;
import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextInput;
import com.extremecoder.mvel.rulesengine.rules.context.RuleEngineContextOutput;
import com.extremecoder.mvel.rulesengine.rules.engine.RuleEngine;
import com.extremecoder.mvel.rulesengine.services.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/api/v1")
@RestController
public class RulesController {

    @Qualifier("")
    private KnowledgeBaseService<RuleResource,Long> knowledgeBaseAdminService;

    private RuleEngine ruleEngine;

    public RulesController(KnowledgeBaseService knowledgeBaseAdminService,RuleEngine ruleEngine){
        this.ruleEngine = ruleEngine;
        this.knowledgeBaseAdminService = knowledgeBaseAdminService;
    }

    @GetMapping(name = "getRulesByNameSpace",path = "/getRulesByNameSpace/{nameSpace}", consumes = "application/json")
    public List<RuleResource> getRulesByNameSpace(@PathVariable("nameSpace")String nameSpace){
        return this
                .knowledgeBaseAdminService
                .findRulesByNameSpace(nameSpace);
    }


    @PostMapping(name = "evaluate",path = "/evaluate", consumes = "application/json")
    public RuleEngineContextOutput evaluate(@RequestBody RuleEngineContextInput input){
        return this.ruleEngine.evaluate(input);
    }

    @PostMapping(name = "save",path = "/rules", consumes = "application/json")
    public RuleResource save(@RequestBody RuleResource input){
        return this.knowledgeBaseAdminService.saveAndFlush(input);
    }

    @PostMapping(name = "saveAll",path = "/rules/bulk", consumes = "application/json")
    public List<RuleResource> saveAll(@RequestBody List<RuleResource> rules){
        return this.knowledgeBaseAdminService.save(rules);
    }
}
