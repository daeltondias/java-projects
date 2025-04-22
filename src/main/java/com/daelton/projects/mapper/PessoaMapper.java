
package com.daelton.projects.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.daelton.projects.entity.PessoaEntity;
import com.daelton.projects.request.PessoaRequest;

@Mapper(componentModel = "spring")
public interface PessoaMapper {
  PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

  @Mapping(target = "id", ignore = true)
  PessoaEntity toEntity(PessoaRequest request);
}
