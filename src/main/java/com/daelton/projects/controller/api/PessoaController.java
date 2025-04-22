package com.daelton.projects.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import com.daelton.projects.request.PessoaRequest;
import com.daelton.projects.service.PessoaService;
import com.daelton.projects.dto.PessoaDTO;

import java.util.NoSuchElementException;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class PessoaController {

  @Autowired
  private PessoaService service;

  @GetMapping
  public ResponseEntity<List<PessoaDTO>> listUsers() {
    try {
      List<PessoaDTO> pessoas = service.listUsers();
      return ResponseEntity.status(200).body(pessoas);
    } catch (Exception e) {
      return ResponseEntity.status(500).build();
    }
  }

  @PostMapping("/new")
  public ResponseEntity<PessoaDTO> createUser(@RequestBody @Valid PessoaRequest body) {
    try {
      PessoaDTO pessoa = service.createUser(body);
      String location = pessoa.getId().toString();
      return ResponseEntity.status(201).header("Location", location).body(pessoa);
    } catch (Exception e) {
      return ResponseEntity.status(400).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<PessoaDTO> getUser(@PathVariable String id) {
    try {
      PessoaDTO pessoa = service.getUser(id);
      return ResponseEntity.status(200).body(pessoa);
    } catch (NumberFormatException | NoSuchElementException e) {
      return ResponseEntity.status(404).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).build();
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<PessoaDTO> updateUser(@PathVariable String id,
      @RequestBody @Valid PessoaRequest body) {
    try {
      PessoaDTO pessoa = service.updateUser(id, body);
      return ResponseEntity.status(200).body(pessoa);
    } catch (NumberFormatException | NoSuchElementException e) {
      return ResponseEntity.status(404).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).build();
    }
  }

}
