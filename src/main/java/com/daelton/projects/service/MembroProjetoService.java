package com.daelton.projects.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daelton.projects.repository.MembroProjetoRepository;
import com.daelton.projects.repository.PessoaRepository;
import com.daelton.projects.request.MembroProjetoRequest;

import jakarta.transaction.Transactional;

import com.daelton.projects.entity.MembroProjetoEntity;
import com.daelton.projects.mapper.MembroProjetoMapper;
import com.daelton.projects.dto.MembroProjetoDTO;
import com.daelton.projects.entity.PessoaEntity;

@Service
public class MembroProjetoService {
  @Autowired
  private MembroProjetoRepository membroRepository;

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private MembroProjetoMapper mapper;

  public MembroProjetoDTO addMember(MembroProjetoRequest body) {
    PessoaEntity pessoa = pessoaRepository.findByIdAndFuncionarioTrue(body.idpessoa());
    MembroProjetoEntity membroProjetoEntity = mapper.toEntity(body);
    membroProjetoEntity.setPessoa(pessoa);
    membroRepository.save(membroProjetoEntity);
    MembroProjetoDTO membro = new MembroProjetoDTO(membroProjetoEntity);
    return membro;
  }

  @Transactional
  public void removedMember(MembroProjetoRequest body) {
    membroRepository.deleteByPessoaIdAndIdprojeto(body.idpessoa(), body.idprojeto());
  }
}
