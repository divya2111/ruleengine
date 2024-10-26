package com.app1.ruleengine.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app1.ruleengine.model.Rule;
import com.app1.ruleengine.model.RuleNode;
import com.app1.ruleengine.repository.RuleRepository;
import com.app1.ruleengine.service.RuleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class RuleEvaluationController {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private RuleRepository ruleRepository;

    @PostMapping("/api/rules/evaluate")
    public ResponseEntity<Boolean> evaluateRule(@RequestBody Map<String, Object> data) {
        RuleNode rootNode = retrieveRootNode();
        if (rootNode == null) {
            return ResponseEntity.badRequest().body(false);
        }
        boolean result = ruleService.evaluateRule(rootNode, data);
        return ResponseEntity.ok(result);
    }

    private RuleNode retrieveRootNode() {
        Rule rule = ruleRepository.findFirstByOrderByIdDesc();
        if (rule == null) {
            System.out.println("No rules found in the database.");
            return null;
        }
        System.out.println("Retrieved AST JSON: " + rule.getAstJson());

        try {
            String astJson = rule.getAstJson();
            if (astJson == null || astJson.trim().isEmpty()) {
                throw new RuntimeException("AST JSON is empty for the retrieved rule.");
            }
            return new ObjectMapper().readValue(astJson, RuleNode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while retrieving AST from JSON", e);
        }
    }
}

