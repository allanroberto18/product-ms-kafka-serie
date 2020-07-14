package com.alr.product.infrastructure.repositories;

import com.alr.product.ProductApplication;
import com.alr.product.domain.entities.Group;
import com.alr.product.domain.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = { ProductApplication.class })
@ActiveProfiles("test")
public class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private GroupRepository groupRepository;


  private Group group;

  @BeforeEach
  void setUp() {
    group = Group.builder()
        .name("Group 1")
        .build();

    groupRepository.save(group);

    Product productExpected = Product.builder()
        .name("user 1")
        .group(group)
        .price(1D)
        .build();

    productRepository.save(productExpected);
  }

  @Test
  public void save_WithObject_MustReturnNewProduct() {
    Product productExpected = Product.builder()
        .name("user 2")
        .group(group)
        .price(2D)
        .build();

    Product product = productRepository.save(productExpected);

    Assertions.assertNotNull(product.getId());
    Assertions.assertEquals(productExpected.getName(), product.getName());
    Assertions.assertEquals(productExpected.getGroup().getId(), product.getGroup().getId());
    Assertions.assertEquals(productExpected.getPrice(), product.getPrice());
  }

  @Test
  public void save_WithId_MustReturnNewProduct() {
    Product productExpected = Product.builder()
        .id(1)
        .name("user 2")
        .group(group)
        .price(2D)
        .build();

    Product product = productRepository.save(productExpected);

    Assertions.assertEquals(productExpected.getName(), product.getName());
    Assertions.assertEquals(productExpected.getGroup().getId(), product.getGroup().getId());
    Assertions.assertEquals(productExpected.getPrice(), product.getPrice());
  }

  @Test
  public void findById_WithValidId_MustReturnAnOptionalOfProduct() {
    Integer id = 1;

    Optional<Product> optProduct = productRepository.findById(id);

    Assertions.assertTrue(optProduct.isPresent());
  }


  @Test
  public void findAll_NoArguments_MustReturnAListOfProduct() {
    List<Product> products = productRepository.findAll();

    Assertions.assertTrue(products.size() > 0);
  }
}
