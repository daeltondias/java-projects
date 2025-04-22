package com.daelton.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daelton.projects.entity.MembroProjetoEntity;

public interface MembroProjetoRepository extends JpaRepository<MembroProjetoEntity, Number> {
  void deleteByPessoaIdAndIdprojeto(Long idpessoa, Long idprojeto);

  void deleteAllByIdprojeto(Long idprojeto);
}
