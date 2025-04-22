package com.daelton.projects.controller.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.daelton.projects.dto.PessoaDTO;
import com.daelton.projects.request.PessoaRequest;
import com.daelton.projects.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private PessoaService pessoaService;

  @Autowired
  private ObjectMapper objectMapper;

  private PessoaDTO pessoaDTO1;
  private PessoaDTO pessoaDTO2;
  private PessoaRequest validRequest;

  @BeforeEach
  void setUp() {
    // Setup test data
    pessoaDTO1 = new PessoaDTO();
    pessoaDTO1.setId(1L);
    pessoaDTO1.setNome("Caio Castro");

    pessoaDTO2 = new PessoaDTO();
    pessoaDTO2.setId(2L);
    pessoaDTO2.setNome("Caio Castro");

    validRequest = new PessoaRequest(
        "Caio Castro",
        LocalDate.of(1997, 5, 5),
        "456.789.123-77",
        true,
        true);
  }

  @Test
  void listUsers_ReturnsAllUsers_WhenSuccessful() throws Exception {
    // Arrange
    List<PessoaDTO> pessoas = Arrays.asList(pessoaDTO1, pessoaDTO2);
    when(pessoaService.listUsers()).thenReturn(pessoas);

    // Act & Assert
    mockMvc.perform(get("/api/users")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(pessoaDTO1.getId()))
        .andExpect(jsonPath("$[0].nome").value(pessoaDTO1.getNome()))
        .andExpect(jsonPath("$[1].id").value(pessoaDTO2.getId()))
        .andExpect(jsonPath("$[1].nome").value(pessoaDTO2.getNome()));
  }

  @Test
  void listUsers_Returns500_WhenServiceThrowsException() throws Exception {
    // Arrange
    when(pessoaService.listUsers()).thenThrow(new RuntimeException("Database error"));

    // Act & Assert
    mockMvc.perform(get("/api/users")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void createUser_ReturnsCreatedUser_WhenSuccessful() throws Exception {
    // Arrange
    when(pessoaService.createUser(any(PessoaRequest.class))).thenReturn(pessoaDTO1);

    // Act & Assert
    mockMvc.perform(post("/api/users/new")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value(pessoaDTO1.getId()))
        .andExpect(jsonPath("$.nome").value(pessoaDTO1.getNome()));
  }

  @Test
  void createUser_Returns400_WhenServiceThrowsException() throws Exception {
    // Arrange
    when(pessoaService.createUser(any(PessoaRequest.class))).thenThrow(new RuntimeException("Invalid data"));

    // Act & Assert
    mockMvc.perform(post("/api/users/new")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getUser_ReturnsUser_WhenFound() throws Exception {
    // Arrange
    String userId = "1";
    when(pessoaService.getUser(userId)).thenReturn(pessoaDTO1);

    // Act & Assert
    mockMvc.perform(get("/api/users/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(pessoaDTO1.getId()))
        .andExpect(jsonPath("$.nome").value(pessoaDTO1.getNome()));
  }

  @Test
  void getUser_Returns404_WhenUserNotFound() throws Exception {
    // Arrange
    String userId = "999";
    when(pessoaService.getUser(userId)).thenThrow(new NoSuchElementException("User not found"));

    // Act & Assert
    mockMvc.perform(get("/api/users/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void getUser_Returns404_WhenInvalidIdFormat() throws Exception {
    // Arrange
    String invalidId = "abc";
    when(pessoaService.getUser(invalidId)).thenThrow(new NumberFormatException("Invalid ID format"));

    // Act & Assert
    mockMvc.perform(get("/api/users/{id}", invalidId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void getUser_Returns400_WhenOtherExceptionOccurs() throws Exception {
    // Arrange
    String userId = "1";
    when(pessoaService.getUser(userId)).thenThrow(new RuntimeException("Unexpected error"));

    // Act & Assert
    mockMvc.perform(get("/api/users/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateUser_ReturnsUpdatedUser_WhenSuccessful() throws Exception {
    // Arrange
    String userId = "1";
    when(pessoaService.updateUser(anyString(), any(PessoaRequest.class))).thenReturn(pessoaDTO1);

    // Act & Assert
    mockMvc.perform(patch("/api/users/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(pessoaDTO1.getId()))
        .andExpect(jsonPath("$.nome").value(pessoaDTO1.getNome()));
  }

  @Test
  void updateUser_Returns404_WhenUserNotFound() throws Exception {
    // Arrange
    String userId = "999";
    when(pessoaService.updateUser(anyString(), any(PessoaRequest.class)))
        .thenThrow(new NoSuchElementException("User not found"));

    // Act & Assert
    mockMvc.perform(patch("/api/users/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateUser_Returns404_WhenInvalidIdFormat() throws Exception {
    // Arrange
    String invalidId = "abc";
    when(pessoaService.updateUser(anyString(), any(PessoaRequest.class)))
        .thenThrow(new NumberFormatException("Invalid ID format"));

    // Act & Assert
    mockMvc.perform(patch("/api/users/{id}", invalidId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateUser_Returns400_WhenOtherExceptionOccurs() throws Exception {
    // Arrange
    String userId = "1";
    when(pessoaService.updateUser(anyString(), any(PessoaRequest.class)))
        .thenThrow(new RuntimeException("Unexpected error"));

    // Act & Assert
    mockMvc.perform(patch("/api/users/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isBadRequest());
  }
}
