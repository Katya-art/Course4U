package org.example.finalProjectSpring.dao;

import org.example.finalProjectSpring.model.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionDao extends JpaRepository<Condition, Long> {
}
