package com.daelton.projects.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import com.daelton.projects.request.MembroProjetoRequest;
import com.daelton.projects.service.MembroProjetoService;
import com.daelton.projects.dto.MembroProjetoDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MembroProjetoController {

  @Autowired
  private MembroProjetoService service;

  @PostMapping("/add")
  public ResponseEntity<Void> addMember(@RequestBody @Valid MembroProjetoRequest body) {
    try {
      MembroProjetoDTO membro = service.addMember(body);
      String location = membro.getId().toString();
      return ResponseEntity.status(201).header("Location", location).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).build();
    }
  }

  @DeleteMapping("/remove")
  public ResponseEntity<Void> removeMember(@RequestBody @Valid MembroProjetoRequest body) {
    try {
      service.removedMember(body);
      return ResponseEntity.status(204).build();
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).build();
    }
  }
}
