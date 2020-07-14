package com.alr.product.domain.contracts.services;

import com.alr.product.domain.entities.Product;
import com.alr.product.domain.entities.ProductView;
import com.alr.product.infrastructure.requests.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
  Product save(final ProductDTO productDTO);
  Product save(final Integer id, final ProductDTO productDTO);
  Optional<ProductView> find(final Integer id);
  List<ProductView> all();
}
