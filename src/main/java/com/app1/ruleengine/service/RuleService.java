package com.app1.ruleengine.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app1.ruleengine.model.Rule;
import com.app1.ruleengine.model.RuleNode;
import com.app1.ruleengine.repository.RuleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RuleService {

    private static final Logger logger = LoggerFactory.getLogger(RuleService.class);

    @Autowired
    private RuleRepository ruleRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RuleNode createRule(String ruleString) {
        RuleNode rootNode = parseToAST(ruleString);
        saveRule(ruleString, rootNode);
        return rootNode;
    }

    private void saveRule(String ruleString, RuleNode ast) {
        try {
            String astJson = objectMapper.writeValueAsString(ast);
            Rule rule = new Rule(ruleString, astJson);
            ruleRepository.save(rule);
            logger.info("Saved rule to database: {}", ruleString);
        } catch (JsonProcessingException e) {
            logger.error("Error while saving rule AST to JSON format", e);
            throw new RuntimeException("Error while saving rule AST to JSON format", e);
        }
    }

    public boolean evaluateRule(RuleNode root, Map<String, Object> data) {
        if (root == null) return false;

        logger.info("Evaluating rule with root type: {}", root.getType());

        if ("operand".equals(root.getType())) {
            logger.info("Root Node: Field = {}, Value = {}, Condition = {}",
                    root.getField(), root.getValue(), root.getCondition());

            boolean conditionResult = evaluateCondition(root, data);
            logger.info("Evaluated operand: {} -> {}", root.getValue(), conditionResult);
            return conditionResult;
        } else if ("operator".equals(root.getType())) {
            boolean leftResult = evaluateRule(root.getLeft(), data);
            boolean rightResult = evaluateRule(root.getRight(), data);

            boolean result = "AND".equals(root.getValue()) ? leftResult && rightResult
                    : "OR".equals(root.getValue()) ? leftResult || rightResult
                    : false;

            logger.info("Evaluated operator: {} -> Left: {}, Right: {}, Result: {}",
                    root.getValue(), leftResult, rightResult, result);
            return result;
        }
        return false;
    }

    private RuleNode parseToAST(String ruleString) {
        ruleString = ruleString.trim();

        // Check for AND/OR and handle precedence
        if (ruleString.contains("AND") || ruleString.contains("OR")) {
            String operator = ruleString.contains("AND") ? "AND" : "OR";
            int operatorIndex = ruleString.indexOf(operator);

            // Create the operator node
            if (operatorIndex > 0 && operatorIndex < ruleString.length() - operator.length()) {
                RuleNode root = new RuleNode();
                root.setType("operator");
                root.setValue(operator);

                // Recursively parse left and right sub-expressions
                root.setLeft(parseToAST(ruleString.substring(0, operatorIndex).trim()));
                root.setRight(parseToAST(ruleString.substring(operatorIndex + operator.length()).trim()));

                return root;
            } else {
                throw new IllegalArgumentException("Invalid rule format: " + ruleString);
            }
        } else {
            // Create an operand node
            RuleNode leaf = new RuleNode();
            leaf.setType("operand");
            String[] parts = ruleString.split(" ");
            if (parts.length == 3) {
                leaf.setField(parts[0]); // e.g., "experience"
                leaf.setCondition(parts[1]); // e.g., ">="
                leaf.setValue(parts[2]); // e.g., "3"
            } else {
                throw new IllegalArgumentException("Invalid operand format: " + ruleString);
            }
            return leaf;
        
        }
    }

    private boolean evaluateCondition(RuleNode root, Map<String, Object> data) {
        if (data == null) {
            logger.error("Data map is null.");
            return false;
        }

        String attribute = root.getField();
        String condition = root.getCondition();
        String value = root.getValue();

        Object actualValue = data.get(attribute);
        logger.info("Evaluating condition: Attribute = {}, Condition = {}, Value = {}", attribute, condition, value);

        if (actualValue == null) {
            logger.warn("Attribute not found in data: {}", attribute);
            return false;
        }

        try {
            double actualValueNumeric = ((Number) actualValue).doubleValue();
            double comparisonValueNumeric = Double.parseDouble(value);

            logger.info("Actual value for {}: {}", attribute, actualValueNumeric);
            logger.info("Comparing with value: {}", comparisonValueNumeric);

            switch (condition) {
                case "greater_than_or_equal":
                    return actualValueNumeric >= comparisonValueNumeric;
                case "less_than_or_equal":
                    return actualValueNumeric <= comparisonValueNumeric;
                case "greater_than":
                    return actualValueNumeric > comparisonValueNumeric;
                case "less_than":
                    return actualValueNumeric < comparisonValueNumeric;
                case "equal":
                case "==":
                    return actualValueNumeric == comparisonValueNumeric; // Ensure comparison is numeric
                case "not_equal":
                case "!=":
                    return actualValueNumeric != comparisonValueNumeric; // Ensure comparison is numeric
                default:
                    throw new IllegalArgumentException("Invalid condition: " + condition);
            }
        } catch (ClassCastException e) {
            logger.error("Type mismatch for attribute: {}. Expected Number, but found: {}", attribute, actualValue.getClass().getSimpleName());
            return false;
        } catch (NumberFormatException e) {
            logger.error("Invalid number format for value: {}", value, e);
            return false;
        }
    }
}
