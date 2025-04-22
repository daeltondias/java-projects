package com.daelton.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daelton.projects.entity.PessoaEntity;

import java.util.List;

public interface PessoaRepository extends JpaRepository<PessoaEntity, Number> {
  List<PessoaEntity> findByGerenteTrue();

  List<PessoaEntity> findByFuncionarioTrue();

  PessoaEntity findByIdAndFuncionarioTrue(Long id);

  List<PessoaEntity> findByFuncionarioTrueAndIdNotIn(List<Long> ids);

}
