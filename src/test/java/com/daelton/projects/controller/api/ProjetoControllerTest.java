package com.daelton.projects.controller.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.daelton.projects.dto.ProjetoDTO;
import com.daelton.projects.request.ProjetoResquest;
import com.daelton.projects.request.ProjetoResquestOptional;
import com.daelton.projects.service.ProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProjetoController.class)
public class ProjetoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private ProjetoService projetoService;

  private ProjetoDTO mockProjetoDTO;
  private ProjetoResquest mockProjetoRequest;
  private ProjetoResquestOptional mockProjetoRequestOptional;

  @BeforeEach
  void setUp() {
    // Setup mock DTO
    mockProjetoDTO = new ProjetoDTO();
    mockProjetoDTO.setId(1L);
    mockProjetoDTO.setNome("Projeto Teste");

    // Setup mock request
    mockProjetoRequest = new ProjetoResquest(
        "Projeto Alpha",
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2025, 12, 1),
        "Projeto de modernização",
        new BigDecimal("100000.00"),
        "Baixo risco",
        1L);

    // Setup mock optional request
    mockProjetoRequestOptional = new ProjetoResquestOptional(
        "Novo nome",
        null,
        null,
        "Nova descrição",
        null,
        null,
        null);
  }

  @Test
  void listProjects_ReturnsProjects_WhenSuccessful() throws Exception {
    List<ProjetoDTO> projetoList = Arrays.asList(mockProjetoDTO);
    when(projetoService.listProjects()).thenReturn(projetoList);

    mockMvc.perform(get("/api/projects")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(mockProjetoDTO.getId()))
        .andExpect(jsonPath("$[0].nome").value(mockProjetoDTO.getNome()));
  }

  @Test
  void listProjects_Returns500_WhenServiceThrowsException() throws Exception {
    when(projetoService.listProjects()).thenThrow(new RuntimeException("Database error"));

    mockMvc.perform(get("/api/projects")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void createProject_ReturnsCreatedProject_WhenSuccessful() throws Exception {
    when(projetoService.createProject(any(ProjetoResquest.class))).thenReturn(mockProjetoDTO);

    mockMvc.perform(post("/api/projects/new")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mockProjetoRequest)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value(mockProjetoDTO.getId()))
        .andExpect(jsonPath("$.nome").value(mockProjetoDTO.getNome()));
  }

  @Test
  void createProject_Returns400_WhenServiceThrowsException() throws Exception {
    when(projetoService.createProject(any(ProjetoResquest.class))).thenThrow(new RuntimeException("Invalid data"));

    mockMvc.perform(post("/api/projects/new")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mockProjetoRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getProject_ReturnsProject_WhenSuccessful() throws Exception {
    when(projetoService.getProject("1")).thenReturn(mockProjetoDTO);

    mockMvc.perform(get("/api/projects/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(mockProjetoDTO.getId()))
        .andExpect(jsonPath("$.nome").value(mockProjetoDTO.getNome()));
  }

  @Test
  void getProject_Returns404_WhenProjectNotFound() throws Exception {
    when(projetoService.getProject("999")).thenThrow(new NoSuchElementException("Project not found"));

    mockMvc.perform(get("/api/projects/999")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void getProject_Returns404_WhenIdIsInvalidFormat() throws Exception {
    when(projetoService.getProject("invalid")).thenThrow(new NumberFormatException("Invalid ID format"));

    mockMvc.perform(get("/api/projects/invalid")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateProject_ReturnsUpdatedProject_WhenSuccessful() throws Exception {
    when(projetoService.updateProject(anyString(), any(ProjetoResquestOptional.class))).thenReturn(mockProjetoDTO);

    mockMvc.perform(patch("/api/projects/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mockProjetoRequestOptional)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(mockProjetoDTO.getId()))
        .andExpect(jsonPath("$.nome").value(mockProjetoDTO.getNome()));
  }

  @Test
  void updateProject_Returns404_WhenProjectNotFound() throws Exception {
    when(projetoService.updateProject(anyString(), any(ProjetoResquestOptional.class)))
        .thenThrow(new NoSuchElementException("Project not found"));

    mockMvc.perform(patch("/api/projects/999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mockProjetoRequestOptional)))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteProject_Returns204_WhenSuccessful() throws Exception {
    doNothing().when(projetoService).deleteProject("1");

    mockMvc.perform(delete("/api/projects/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteProject_Returns404_WhenProjectNotFound() throws Exception {
    doThrow(new NoSuchElementException("Project not found")).when(projetoService).deleteProject("999");

    mockMvc.perform(delete("/api/projects/999")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteProject_Returns409_WhenProjectCannotBeDeleted() throws Exception {
    doThrow(new IllegalStateException("Project cannot be deleted")).when(projetoService).deleteProject("1");

    mockMvc.perform(delete("/api/projects/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
  }

  @Test
  void cancelProject_Returns204_WhenSuccessful() throws Exception {
    doNothing().when(projetoService).cancelProject("1");

    mockMvc.perform(patch("/api/projects/cancel/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void cancelProject_Returns404_WhenProjectNotFound() throws Exception {
    doThrow(new NoSuchElementException("Project not found")).when(projetoService).cancelProject("999");

    mockMvc.perform(patch("/api/projects/cancel/999")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void advanceProject_Returns204_WhenSuccessful() throws Exception {
    doNothing().when(projetoService).advanceProject("1");

    mockMvc.perform(patch("/api/projects/advance/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void advanceProject_Returns404_WhenProjectNotFound() throws Exception {
    doThrow(new NoSuchElementException("Project not found")).when(projetoService).advanceProject("999");

    mockMvc.perform(patch("/api/projects/advance/999")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void advanceProject_Returns400_WhenServiceThrowsException() throws Exception {
    doThrow(new RuntimeException("Cannot advance project")).when(projetoService).advanceProject("1");

    mockMvc.perform(patch("/api/projects/advance/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
