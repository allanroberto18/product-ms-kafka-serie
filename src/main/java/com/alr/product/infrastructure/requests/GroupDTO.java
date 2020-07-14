package com.alr.product.infrastructure.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class GroupDTO {
  @NotBlank(message = "Field name is required")
  private String name;
}
