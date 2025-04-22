package com.daelton.projects.controller.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.daelton.projects.dto.MembroProjetoDTO;
import com.daelton.projects.request.MembroProjetoRequest;
import com.daelton.projects.service.MembroProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MembroProjetoController.class)
public class MembroProjetoControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private MembroProjetoService membroProjetoService;

  @Autowired
  private ObjectMapper objectMapper;

  private MembroProjetoRequest validRequest;
  private MembroProjetoDTO membroProjetoDTO;

  @BeforeEach
  void setUp() {
    // Setup test data
    validRequest = new MembroProjetoRequest(1L, 1L);

    membroProjetoDTO = new MembroProjetoDTO();
    membroProjetoDTO.setId(1L);
  }

  @Test
  void addMember_Success() throws Exception {
    when(membroProjetoService.addMember(any(MembroProjetoRequest.class))).thenReturn(membroProjetoDTO);

    mockMvc.perform(post("/api/members/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "1"));
  }

  @Test
  void addMember_Failure() throws Exception {
    when(membroProjetoService.addMember(any(MembroProjetoRequest.class)))
        .thenThrow(new RuntimeException("Error adding member"));

    mockMvc.perform(post("/api/members/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void removeMember_Success() throws Exception {
    mockMvc.perform(delete("/api/members/remove")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isNoContent());
  }

  @Test
  void removeMember_Failure() throws Exception {
    doThrow(new RuntimeException("Error removing member")).when(membroProjetoService)
        .removedMember(any(MembroProjetoRequest.class));

    mockMvc.perform(delete("/api/members/remove")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isBadRequest());
  }
}
