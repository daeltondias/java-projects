package com.daelton.projects.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Table(name = "membro_projeto")
@Entity(name = "membro_projeto")
public class MembroProjetoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "idpessoa")
  private PessoaEntity pessoa;

  private Long idprojeto;
}
