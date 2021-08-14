package org.example.finalProjectSpring.dao;

import org.example.finalProjectSpring.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkDao extends JpaRepository<Mark, Long> {
    Mark findMarkById(Long id);
}
