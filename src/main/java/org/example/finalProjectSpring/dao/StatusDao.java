package org.example.finalProjectSpring.dao;

import org.example.finalProjectSpring.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusDao extends JpaRepository<Status, Long> {
}
