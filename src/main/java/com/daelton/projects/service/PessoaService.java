package com.daelton.projects.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daelton.projects.repository.PessoaRepository;
import com.daelton.projects.request.PessoaRequest;
import com.daelton.projects.mapper.PessoaMapper;
import com.daelton.projects.entity.PessoaEntity;
import com.daelton.projects.dto.PessoaDTO;

import org.modelmapper.ModelMapper;

import java.util.List;

@Service
public class PessoaService {

  @Autowired
  private PessoaMapper mapper;

  @Autowired
  private PessoaRepository repository;

  public List<PessoaDTO> listUsers() {
    List<PessoaDTO> pessoas = repository.findAll()
        .stream()
        .map(PessoaDTO::new).toList();
    return pessoas;
  }

  public PessoaDTO createUser(PessoaRequest body) {
    PessoaEntity pessoaEntity = mapper.toEntity(body);
    repository.save(pessoaEntity);
    PessoaDTO pessoa = new PessoaDTO(pessoaEntity);
    return pessoa;
  }

  public PessoaDTO getUser(String id) {
    Long pessoaID = Long.parseLong(id);
    PessoaEntity pessoaEntity = repository.findById(pessoaID).get();
    PessoaDTO projeto = new PessoaDTO(pessoaEntity);
    return projeto;
  }

  public PessoaDTO updateUser(String id, PessoaRequest body) throws Exception {
    Long pessoaID = Long.parseLong(id);
    PessoaEntity pessoaEntity = repository.findById(pessoaID).get();
    PessoaEntity pessoaBody = mapper.toEntity(body);
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setSkipNullEnabled(true);
    modelMapper.map(pessoaBody, pessoaEntity);
    repository.save(pessoaEntity);
    PessoaDTO pessoa = new PessoaDTO(pessoaEntity);
    return pessoa;
  }

}
