
package com.daelton.projects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daelton.projects.entity.ProjetoEntity;

public interface ProjetoRepository extends JpaRepository<ProjetoEntity, Number> {
  List<ProjetoEntity> findByOrderByIdDesc();
}
