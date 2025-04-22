package com.daelton.projects.dto;

import org.springframework.beans.BeanUtils;

import com.daelton.projects.entity.PessoaEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GerenteDTO {
  private Long id;
  private String nome;
  private String cpf;

  public GerenteDTO(PessoaEntity pessoa) {
    BeanUtils.copyProperties(pessoa, this);
  }
}
