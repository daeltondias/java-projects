package com.daelton.projects.dto;

import org.springframework.beans.BeanUtils;

import com.daelton.projects.entity.MembroProjetoEntity;
import com.daelton.projects.entity.PessoaEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MembroProjetoDTO {
  private Long id;
  private String nome;
  private boolean funcionario;
  private boolean gerente;

  public MembroProjetoDTO(MembroProjetoEntity membroProjeto) {
    BeanUtils.copyProperties(membroProjeto, this);
    PessoaEntity pessoa = membroProjeto.getPessoa();
    this.id = pessoa.getId();
    this.nome = pessoa.getNome();
    this.funcionario = pessoa.isFuncionario();
    this.gerente = pessoa.isGerente();
  }
}
