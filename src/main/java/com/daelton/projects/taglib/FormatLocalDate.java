package com.daelton.projects.taglib;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormatLocalDate extends TagSupport {
  private String value;
  private String pattern;

  @Override
  public int doStartTag() throws JspException {
    try {
      if (value == null || pattern == null || value.isEmpty() || pattern.isEmpty()) {
        pageContext.getOut().print("--");
      } else {
        LocalDate date = LocalDate.parse(value);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedDate = date.format(formatter);
        pageContext.getOut().print(formattedDate);
      }
    } catch (Exception e) {
      throw new JspException("Erro ao formatar a data", e);
    }
    return SKIP_BODY; // Ignora o corpo da tag
  }
}
