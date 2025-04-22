package com.daelton.projects.controller;

import com.daelton.projects.entity.PessoaEntity;
import com.daelton.projects.entity.ProjetoEntity;
import com.daelton.projects.entity.MembroProjetoEntity;
import com.daelton.projects.repository.PessoaRepository;
import com.daelton.projects.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class WebProjetoControllerTest {

  @Mock
  private ProjetoRepository projetoRepository;

  @Mock
  private PessoaRepository pessoaRepository;

  @Mock
  private Model model;

  @InjectMocks
  private WebProjetoController controller;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    // Create a view resolver that will resolve views to appropriate templates
    ViewResolver viewResolver = new InternalResourceViewResolver();
    ((InternalResourceViewResolver) viewResolver).setPrefix("/templates/");
    ((InternalResourceViewResolver) viewResolver).setSuffix(".html");

    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setViewResolvers(viewResolver)
        .build();
  }

  @Test
  void homeRedirectsToProjects() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/projects"));
  }

  @Test
  void homeReturnsRedirectString() {
    String viewName = controller.home();
    assertEquals("redirect:/projects", viewName);
  }

  @Test
  void projectsAddsAttributesToModel() {
    // Arrange
    List<PessoaEntity> managers = Arrays.asList(createManager(1L), createManager(2L));
    List<ProjetoEntity> projects = Arrays.asList(createProject(1L), createProject(2L));

    when(pessoaRepository.findByGerenteTrue()).thenReturn(managers);
    when(projetoRepository.findByOrderByIdDesc()).thenReturn(projects);

    // Act
    String viewName = controller.projects(model);

    // Assert
    assertEquals("projects", viewName);
    verify(model).addAttribute("projects", projects);
    verify(model).addAttribute("managers", managers);
  }

  @Test
  void projectsReturnsCorrectViewName() throws Exception {
    // Arrange
    List<PessoaEntity> managers = Arrays.asList(createManager(1L));
    List<ProjetoEntity> projects = Arrays.asList(createProject(1L));

    when(pessoaRepository.findByGerenteTrue()).thenReturn(managers);
    when(projetoRepository.findByOrderByIdDesc()).thenReturn(projects);

    // Act & Assert
    mockMvc.perform(get("/projects"))
        .andExpect(status().isOk())
        .andExpect(view().name("projects"));
  }

  @Test
  void projectDetailsAddsAttributesToModel() {
    // Arrange
    Long projectId = 1L;
    ProjetoEntity project = createProject(projectId);
    List<PessoaEntity> managers = Arrays.asList(createManager(1L));
    List<PessoaEntity> members = Arrays.asList(createEmployee(3L), createEmployee(4L));

    when(projetoRepository.findById(projectId)).thenReturn(Optional.of(project));
    when(pessoaRepository.findByGerenteTrue()).thenReturn(managers);
    when(pessoaRepository.findByFuncionarioTrue()).thenReturn(members);

    // Act
    String viewName = controller.projectDetails(projectId.toString(), model);

    // Assert
    assertEquals("project-details", viewName);
    verify(model).addAttribute("project", project);
    verify(model).addAttribute("managers", managers);
    verify(model).addAttribute("members", members);
  }

  @Test
  void projectDetailsWithMembersExcludesExistingMembers() {
    // Arrange
    Long projectId = 1L;
    Long memberId = 2L;

    PessoaEntity memberPerson = createEmployee(memberId);
    MembroProjetoEntity member = new MembroProjetoEntity();
    member.setPessoa(memberPerson);

    ProjetoEntity project = createProject(projectId);
    project.setMembros(Arrays.asList(member));

    List<PessoaEntity> managers = Arrays.asList(createManager(1L));
    List<PessoaEntity> availableEmployees = Arrays.asList(createEmployee(3L), createEmployee(4L));

    when(projetoRepository.findById(projectId)).thenReturn(Optional.of(project));
    when(pessoaRepository.findByGerenteTrue()).thenReturn(managers);
    when(pessoaRepository.findByFuncionarioTrueAndIdNotIn(anyList())).thenReturn(availableEmployees);

    // Act
    String viewName = controller.projectDetails(projectId.toString(), model);

    // Assert
    assertEquals("project-details", viewName);
    verify(model).addAttribute("project", project);
    verify(model).addAttribute("managers", managers);
    verify(model).addAttribute("members", availableEmployees);
  }

  @Test
  void projectDetailsReturnsCorrectViewName() throws Exception {
    // Arrange
    Long projectId = 1L;
    ProjetoEntity project = createProject(projectId);

    when(projetoRepository.findById(projectId)).thenReturn(Optional.of(project));
    when(pessoaRepository.findByGerenteTrue()).thenReturn(new ArrayList<>());
    when(pessoaRepository.findByFuncionarioTrue()).thenReturn(new ArrayList<>());

    // Act & Assert
    mockMvc.perform(get("/projects/" + projectId))
        .andExpect(status().isOk())
        .andExpect(view().name("project-details"));
  }

  // Helper methods to create test entities
  private PessoaEntity createManager(Long id) {
    PessoaEntity manager = new PessoaEntity();
    manager.setId(id);
    manager.setGerente(true);
    manager.setNome("Manager " + id);
    return manager;
  }

  private PessoaEntity createEmployee(Long id) {
    PessoaEntity employee = new PessoaEntity();
    employee.setId(id);
    employee.setFuncionario(true);
    employee.setNome("Employee " + id);
    return employee;
  }

  private ProjetoEntity createProject(Long id) {
    ProjetoEntity project = new ProjetoEntity();
    project.setId(id);
    project.setNome("Project " + id);
    project.setMembros(new ArrayList<>());
    return project;
  }
}
