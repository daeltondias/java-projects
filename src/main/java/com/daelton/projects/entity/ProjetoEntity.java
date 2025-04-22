package com.daelton.projects.entity;

import com.daelton.projects.validation.GerenteValidator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
@Table(name = "projeto")
@Entity(name = "projeto")
public class ProjetoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String nome;

  private LocalDate data_inicio;
  private LocalDate data_previsao_fim;
  private LocalDate data_fim;

  @Column(length = 5000)
  private String descricao;

  private String status;

  @DecimalMin("0.0")
  private Double orcamento;

  private String risco;

  @ManyToOne
  @GerenteValidator
  @JoinColumn(name = "idgerente", nullable = false)
  private PessoaEntity gerente;

  @OneToMany(mappedBy = "idprojeto")
  private List<MembroProjetoEntity> membros;

  @Transient
  public boolean getPode_excluir() {
    return List.of("iniciado", "em andamento", "encerrado")
        .contains(status.toLowerCase()) ? false : true;
  }
}
