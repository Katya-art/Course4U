package org.example.finalProjectSpring.dao;

import org.example.finalProjectSpring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
