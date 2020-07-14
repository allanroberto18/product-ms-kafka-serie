package com.alr.product.application.mappers;

import com.alr.product.ProductApplication;
import com.alr.product.domain.entities.Group;
import com.alr.product.domain.entities.Product;
import com.alr.product.infrastructure.repositories.GroupRepository;
import com.alr.product.infrastructure.requests.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {ProductApplication.class})
@ActiveProfiles("test")
public class ProductMapperTest {

  @Autowired
  private ProductMapper productMapper;

  @Test
  public void apply_WithProductDTO_MustReturnAProduct() {
    String name = "Product Test";
    Integer groupId = 1;
    Double price = 1D;
    String groupName = "Group Test";

    Group group = Group.builder()
        .id(groupId)
        .name(groupName)
        .build();

    ProductDTO productDTO = ProductDTO.builder()
        .name(name)
        .groupId(1)
        .price(price)
        .build();

    Product product = productMapper.apply(group, productDTO);

    Assertions.assertNotNull(product);
    Assertions.assertEquals(name, product.getName());
    Assertions.assertEquals(price, product.getPrice());
    Assertions.assertEquals(groupId, product.getGroup().getId());
  }
}