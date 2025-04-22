package com.daelton.projects.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import com.daelton.projects.request.ProjetoResquestOptional;
import com.daelton.projects.request.ProjetoResquest;
import com.daelton.projects.service.ProjetoService;
import com.daelton.projects.dto.ProjetoDTO;

import java.util.NoSuchElementException;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjetoController {

  @Autowired
  private ProjetoService service;

  @GetMapping
  public ResponseEntity<List<ProjetoDTO>> listProjects() {
    try {
      List<ProjetoDTO> projetos = service.listProjects();
      return ResponseEntity.status(200).body(projetos);
    } catch (Exception e) {
      return ResponseEntity.status(500).build();
    }
  }

  @PostMapping("/new")
  public ResponseEntity<ProjetoDTO> createProject(@RequestBody @Valid ProjetoResquest body) {
    try {
      ProjetoDTO projeto = service.createProject(body);
      String location = projeto.getId().toString();
      return ResponseEntity.status(201).header("Location", location).body(projeto);
    } catch (Exception e) {
      return ResponseEntity.status(400).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjetoDTO> getProject(@PathVariable String id) {
    try {
      ProjetoDTO projeto = service.getProject(id);
      return ResponseEntity.status(200).body(projeto);
    } catch (NumberFormatException | NoSuchElementException e) {
      return ResponseEntity.status(404).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).build();
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ProjetoDTO> updateProject(@PathVariable String id,
      @RequestBody @Valid ProjetoResquestOptional body) {
    try {
      ProjetoDTO projeto = service.updateProject(id, body);
      return ResponseEntity.status(200).body(projeto);
    } catch (NumberFormatException | NoSuchElementException e) {
      return ResponseEntity.status(404).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProject(@PathVariable String id) {
    try {
      service.deleteProject(id);
      return ResponseEntity.status(204).build();
    } catch (NumberFormatException | NoSuchElementException e) {
      return ResponseEntity.status(404).build();
    } catch (IllegalStateException e) {
      return ResponseEntity.status(409).build();
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).build();
    }
  }

  @PatchMapping("/cancel/{id}")
  public ResponseEntity<Void> cancelProject(@PathVariable String id) {
    try {
      service.cancelProject(id);
      return ResponseEntity.status(204).build();
    } catch (NumberFormatException | NoSuchElementException e) {
      return ResponseEntity.status(404).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).build();
    }
  }

  @PatchMapping("/advance/{id}")
  public ResponseEntity<Void> advanceProject(@PathVariable String id) {
    try {
      service.advanceProject(id);
      return ResponseEntity.status(204).build();
    } catch (NumberFormatException | NoSuchElementException e) {
      return ResponseEntity.status(404).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).build();
    }
  }
}
