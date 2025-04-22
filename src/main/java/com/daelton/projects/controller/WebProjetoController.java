package com.daelton.projects.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.daelton.projects.repository.ProjetoRepository;
import com.daelton.projects.repository.PessoaRepository;
import com.daelton.projects.entity.ProjetoEntity;
import com.daelton.projects.entity.PessoaEntity;

import java.util.List;

@Controller
public class WebProjetoController {
  @Autowired
  private ProjetoRepository repository;

  @Autowired
  private PessoaRepository pessoaRepository;

  @GetMapping("/")
  public String home() {
    return "redirect:/projects";
  }

  @GetMapping("/projects")
  public String projects(Model model) {
    List<PessoaEntity> managers = pessoaRepository.findByGerenteTrue();
    List<ProjetoEntity> projects = repository.findByOrderByIdDesc();
    model.addAttribute("projects", projects);
    model.addAttribute("managers", managers);
    return "projects";
  }

  @GetMapping("/projects/{id}")
  public String projectDetails(@PathVariable String id, Model model) {
    Long projectID = Long.parseLong(id);
    ProjetoEntity project = repository.findById(projectID).get();
    List<Long> excludedIds = project.getMembros().stream().map(member -> member.getPessoa().getId()).toList();
    List<PessoaEntity> members = excludedIds.isEmpty()
        ? pessoaRepository.findByFuncionarioTrue()
        : pessoaRepository.findByFuncionarioTrueAndIdNotIn(excludedIds);
    List<PessoaEntity> managers = pessoaRepository.findByGerenteTrue();
    model.addAttribute("managers", managers);
    model.addAttribute("members", members);
    model.addAttribute("project", project);
    return "project-details";
  }

  // @GetMapping("/projects")
  // public ModelAndView viewProjects(Model model) {
  // List<ProjetoEmtity> projects = repository.findAll();
  // ModelAndView modelAndView = new ModelAndView("projects");
  // modelAndView.addObject("projects", projects);
  // return modelAndView;
  // }
}
