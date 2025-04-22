
package com.daelton.projects.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.daelton.projects.entity.MembroProjetoEntity;
import com.daelton.projects.request.MembroProjetoRequest;

@Mapper(componentModel = "spring")
public interface MembroProjetoMapper {
  MembroProjetoMapper INSTANCE = Mappers.getMapper(MembroProjetoMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "pessoa", ignore = true)
  MembroProjetoEntity toEntity(MembroProjetoRequest request);
}
