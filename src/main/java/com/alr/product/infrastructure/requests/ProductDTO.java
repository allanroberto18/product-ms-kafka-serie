package com.alr.product.infrastructure.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class ProductDTO {
  @NotBlank(message = "Field name is required")
  private String name;

  @NotBlank(message = "Field groupId is required")
  private Integer groupId;

  @NotBlank(message = "Field price is required")
  private Double price;
}
