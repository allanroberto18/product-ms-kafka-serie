package com.alr.product.domain.services;

import com.alr.product.domain.contracts.services.ProductService;
import com.alr.product.domain.entities.Group;
import com.alr.product.domain.entities.Product;
import com.alr.product.domain.entities.ProductView;
import com.alr.product.infrastructure.repositories.GroupRepository;
import com.alr.product.infrastructure.repositories.ProductRepository;
import com.alr.product.infrastructure.repositories.ProductViewRepository;
import com.alr.product.infrastructure.requests.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class ProductServiceImpl implements ProductService {
  private BiFunction<Group, ProductDTO, Product> productMapper;
  private ProductRepository productRepository;
  private GroupRepository groupRepository;
  private ProductViewRepository productViewRepository;

  public ProductServiceImpl(
      BiFunction<Group, ProductDTO, Product> productMapper,
      ProductRepository productRepository,
      GroupRepository groupRepository,
      ProductViewRepository productViewRepository
  ) {
    this.productMapper = productMapper;
    this.productRepository = productRepository;
    this.groupRepository = groupRepository;
    this.productViewRepository = productViewRepository;
  }

  @Override
  public Product save(ProductDTO productDTO) {
    return Optional.of(productDTO)
        .map(dto -> groupRepository.findById(dto.getGroupId())
            .map(group -> productMapper.apply(group, dto))
            .orElseThrow(RuntimeException::new)
        )
        .map(product -> productRepository.save(product))
        .get();
  }

  @Override
  public Product save(Integer id, ProductDTO productDTO) {
    return productRepository.findById(id)
        .map(product -> {
          product.setName(productDTO.getName());
          product.setPrice(productDTO.getPrice());
          checkProduct(productDTO, product);

          return productRepository.save(product);
        })
        .orElseGet(() -> save(productDTO));
  }

  @Override
  public Optional<ProductView> find(Integer id) {
    return productViewRepository.findById(id);
  }

  @Override
  public List<ProductView> all() {
    return productViewRepository.findAll();
  }

  private void checkProduct(ProductDTO productDTO, Product product) {
    if (product.getGroup().getId() != productDTO.getGroupId()) {
      Group group = groupRepository.findById(productDTO.getGroupId())
          .orElseThrow(() -> new RuntimeException("Group not found"));
      product.setGroup(group);
    }
  }
}
