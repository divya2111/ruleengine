package com.app1.ruleengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_string", nullable = false)
    private String ruleString;

    @Column(name = "ast_json", nullable = false, columnDefinition = "TEXT")
    private String astJson;

    // Default constructor required by JPA
    public Rule() {
    }

    // Constructor with parameters
    public Rule(String ruleString, String astJson) {
        this.ruleString = ruleString;
        this.astJson = astJson;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public String getAstJson() {
        return astJson;
    }

    public void setAstJson(String astJson) {
        this.astJson = astJson;
    }

    // Override toString for debugging purposes
    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", ruleString='" + ruleString + '\'' +
                ", astJson='" + astJson + '\'' +
                '}';
    }
}

