package com.daelton.projects.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daelton.projects.repository.MembroProjetoRepository;
import com.daelton.projects.request.ProjetoResquestOptional;

import jakarta.transaction.Transactional;

import com.daelton.projects.mapper.ProjetoMapperOptional;
import com.daelton.projects.repository.ProjetoRepository;
import com.daelton.projects.repository.PessoaRepository;
import com.daelton.projects.request.ProjetoResquest;
import com.daelton.projects.mapper.ProjetoMapper;
import com.daelton.projects.entity.ProjetoEntity;
import com.daelton.projects.entity.PessoaEntity;
import com.daelton.projects.dto.ProjetoDTO;

import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProjetoService {

  @Autowired
  private ProjetoMapper mapper;

  @Autowired
  private ProjetoMapperOptional mapperOptional;

  @Autowired
  private ProjetoRepository repository;

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private MembroProjetoRepository membroProjetoRepository;

  public List<ProjetoDTO> listProjects() {
    List<ProjetoDTO> projetos = repository.findAll()
        .stream()
        .map(ProjetoDTO::new).toList();
    return projetos;
  }

  public ProjetoDTO createProject(ProjetoResquest body) {
    PessoaEntity pessoa = pessoaRepository.findById(body.idgerente()).get();
    ProjetoEntity projetoEntity = mapper.toEntity(body);
    projetoEntity.setMembros(Collections.emptyList());
    projetoEntity.setStatus("em análise");
    projetoEntity.setGerente(pessoa);
    repository.save(projetoEntity);
    ProjetoDTO projeto = new ProjetoDTO(projetoEntity);
    return projeto;
  }

  public ProjetoDTO getProject(String id) {
    Long projetoID = Long.parseLong(id);
    ProjetoEntity projetoEntity = repository.findById(projetoID).get();
    ProjetoDTO projeto = new ProjetoDTO(projetoEntity);
    return projeto;
  }

  public ProjetoDTO updateProject(String id, ProjetoResquestOptional body) throws Exception {
    Long projetoID = Long.parseLong(id);
    ProjetoEntity projetoEntity = repository.findById(projetoID).get();
    if (body.idgerente() != null) {
      PessoaEntity pessoa = pessoaRepository.findById(body.idgerente()).orElseThrow(() -> new Exception());
      projetoEntity.setGerente(pessoa);
    }
    ProjetoEntity projetoBody = mapperOptional.toEntity(body);
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setSkipNullEnabled(true);
    modelMapper.map(projetoBody, projetoEntity);
    repository.save(projetoEntity);
    ProjetoDTO projeto = new ProjetoDTO(projetoEntity);
    return projeto;
  }

  @Transactional
  public void deleteProject(String id) {
    Long projetoID = Long.parseLong(id);
    ProjetoEntity projeto = repository.findById(projetoID).get();
    if (projeto.getPode_excluir() == false) {
      // O projeto não pode ser excluído devido ao seu status atual
      throw new IllegalStateException("Project cannot be deleted due to its current status.");
    }
    membroProjetoRepository.deleteAllByIdprojeto(projetoID);
    repository.deleteById(projetoID);
  }

  public void cancelProject(String id) {
    Long projetoID = Long.parseLong(id);
    ProjetoEntity projeto = repository.findById(projetoID).get();
    projeto.setStatus("cancelado");
    repository.save(projeto);
  }

  public void advanceProject(String id) {
    Long projetoID = Long.parseLong(id);
    ProjetoEntity projeto = repository.findById(projetoID).get();
    if (projeto.getStatus().equals("em andamento")) {
      System.err.println("passou aqui");
      System.err.println(projeto.getStatus());
      projeto.setData_fim(LocalDate.now());
      System.err.println(projeto.getData_fim());
    }
    projeto.setStatus(switch (projeto.getStatus()) {
      case "em análise" -> "análise realizada";
      case "análise realizada" -> "análise aprovada";
      case "análise aprovada" -> "iniciado";
      case "iniciado" -> "planejado";
      case "planejado" -> "em andamento";
      case "em andamento" -> "encerrado";
      default -> projeto.getStatus();
    });
    repository.save(projeto);
  }
}
