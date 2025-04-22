package com.daelton.projects.validation;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.daelton.projects.entity.PessoaEntity;

// Definição da anotação personalizada
@Constraint(validatedBy = GerenteValidator.GerenteValidatorImpl.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GerenteValidator {
  String message() default "A pessoa associada não é um gerente.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  // Implementação do validador
  class GerenteValidatorImpl implements ConstraintValidator<GerenteValidator, PessoaEntity> {
    @Override
    public boolean isValid(PessoaEntity pessoa, ConstraintValidatorContext context) {
      if (pessoa == null) {
        return true; // Permitir nulo, pois a validação de nulidade é feita separadamente
      }
      return pessoa.isGerente(); // Retorna true apenas se a pessoa for gerente
    }
  }
}
