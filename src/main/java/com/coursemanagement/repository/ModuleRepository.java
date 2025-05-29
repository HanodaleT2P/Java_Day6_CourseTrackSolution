package com.coursemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coursemanagement.model.Module;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
