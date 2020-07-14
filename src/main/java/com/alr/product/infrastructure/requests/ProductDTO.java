package com.alr.product.infrastructure.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
  @NotBlank(message = "Field name is required")
  private String name;

  @NotNull(message = "Field groupId is required")
  private Integer groupId;

  @NotNull(message = "Field price is required")
  private Double price;
}
