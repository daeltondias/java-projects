package com.daelton.projects.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.daelton.projects.entity.ProjetoEntity;
import com.daelton.projects.request.ProjetoResquestOptional;

@Mapper(componentModel = "spring")
public interface ProjetoMapperOptional {
  ProjetoMapperOptional INSTANCE = Mappers.getMapper(ProjetoMapperOptional.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "gerente", ignore = true)
  @Mapping(target = "membros", ignore = true)
  @Mapping(target = "data_fim", ignore = true)
  ProjetoEntity toEntity(ProjetoResquestOptional request);
}
