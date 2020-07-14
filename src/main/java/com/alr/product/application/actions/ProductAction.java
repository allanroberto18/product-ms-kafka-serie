package com.alr.product.application.actions;


import com.alr.product.domain.contracts.services.ProductService;
import com.alr.product.domain.entities.Product;
import com.alr.product.domain.entities.ProductView;
import com.alr.product.infrastructure.requests.ProductDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Cacheable(cacheNames = { "products", "product" })
public class ProductAction {
  private ProductService productService;

  public ProductAction(ProductService productService) {
    this.productService = productService;
  }

  @Cacheable(cacheNames = "product", key = "#result.get().id", unless = "#result.empty()")
  public Optional<ProductView> getProduct(final Integer id) {
    return productService.find(id);
  }

  @Cacheable(cacheNames = "products")
  public List<ProductView> getProducts() {
    return productService.all();
  }

  @Caching(
      put = { @CachePut("products") },
      cacheable = { @Cacheable(cacheNames = "product", key = "#result.id") }
  )
  public Product save(final ProductDTO productDTO) {
    return productService.save(productDTO);
  }

  @Caching(
      put = { @CachePut("products") },
      cacheable = { @Cacheable(cacheNames = "product", key = "#result.id") }
  )
  public Product save(final Integer id, final ProductDTO productDTO) {
    return productService.save(id, productDTO);
  }
}
