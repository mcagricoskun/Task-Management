package com.btk.bsd.repository;

import com.btk.bsd.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepositor extends JpaRepository<Project, Long> {
}
