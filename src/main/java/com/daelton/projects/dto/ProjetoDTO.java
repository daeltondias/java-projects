package com.daelton.projects.dto;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.daelton.projects.entity.ProjetoEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjetoDTO {
  private Long id;
  private String nome;
  private LocalDate data_inicio;
  private LocalDate data_previsao_fim;
  private LocalDate data_fim;
  private String descricao;
  private String status;
  private Double orcamento;
  private String risco;
  private GerenteDTO gerente;
  private List<MembroProjetoDTO> membros;
  private Boolean pode_excluir;

  public ProjetoDTO(ProjetoEntity projeto) {
    BeanUtils.copyProperties(projeto, this);
    this.gerente = new GerenteDTO(projeto.getGerente());
    this.membros = projeto.getMembros().stream().map(MembroProjetoDTO::new)
        .collect(Collectors.toList());
  }
}
