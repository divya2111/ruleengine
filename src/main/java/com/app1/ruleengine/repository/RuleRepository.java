package com.app1.ruleengine.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app1.ruleengine.model.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long>{

	Rule findFirstByOrderByIdDesc();
	
}
