
package com.daelton.projects.dto;

import org.springframework.beans.BeanUtils;

import com.daelton.projects.entity.PessoaEntity;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaDTO {
  private Long id;
  private String nome;
  private LocalDate datanascimento;
  private String cpf;
  private boolean funcionario;
  private boolean gerente;

  public PessoaDTO(PessoaEntity pessoa) {
    BeanUtils.copyProperties(pessoa, this);
  }
}
