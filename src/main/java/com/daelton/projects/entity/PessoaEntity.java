package com.daelton.projects.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.time.LocalDate;

import lombok.Data;

@Data
@Table(name = "pessoa")
@Entity(name = "pessoa")
public class PessoaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, nullable = false)
  private String nome;

  private LocalDate datanascimento;

  @Column(length = 14)
  private String cpf;

  private boolean funcionario;

  private boolean gerente;
}
