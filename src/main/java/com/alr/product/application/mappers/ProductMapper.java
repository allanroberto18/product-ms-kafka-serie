package com.alr.product.application.mappers;

import com.alr.product.domain.entities.Group;
import com.alr.product.domain.entities.Product;
import com.alr.product.infrastructure.requests.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class ProductMapper implements BiFunction<Group, ProductDTO, Product> {

  @Override
  public Product apply(Group group, ProductDTO productDTO) {
    return Product.builder()
        .name(productDTO.getName())
        .group(group)
        .price(productDTO.getPrice())
        .build();
  }
}
