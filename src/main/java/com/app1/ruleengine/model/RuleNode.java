package com.app1.ruleengine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleNode {
	 private String type;  // "operator" or "operand"
	    private RuleNode left;
	    private RuleNode right;
	    private String value; // condition for operand
	    private String field;
	    private String condition;

    public RuleNode(String type, RuleNode left, RuleNode right, String value, String field) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
        this.field = field;
    }
    public RuleNode() {}
    public RuleNode(String type, String value, String field) {
        this.type = type;
        this.value = value;
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public RuleNode getLeft() {
        return left;
    }

    public RuleNode getRight() {
        return right;
    }

    public String getValue() {
        return value;
    }

    public void setLeft(RuleNode left) {
        this.left = left;
    }

    public void setRight(RuleNode right) {
        this.right = right;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
