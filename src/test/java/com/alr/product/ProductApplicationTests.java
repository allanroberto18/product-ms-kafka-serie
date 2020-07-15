package com.alr.product;

import com.alr.product.application.actions.GroupAction;
import com.alr.product.application.actions.ProductAction;
import com.alr.product.application.mappers.GroupMapper;
import com.alr.product.application.mappers.ProductMapper;
import com.alr.product.domain.contracts.services.GroupService;
import com.alr.product.domain.contracts.services.ProductService;
import com.alr.product.infrastructure.controllers.DocumentationController;
import com.alr.product.infrastructure.repositories.GroupRepository;
import com.alr.product.infrastructure.repositories.ProductRepository;
import com.alr.product.infrastructure.repositories.ProductViewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProductApplicationTests {

  @Autowired
  private DocumentationController documentationController;

  @Autowired
  private GroupMapper groupMapper;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private GroupAction groupAction;

  @Autowired
  private GroupService groupService;

  @Autowired
  private ProductMapper productMapper;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductViewRepository productViewRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductAction productAction;

  @Test
  void contextLoads() {
    Assertions.assertNotNull(documentationController);
    Assertions.assertNotNull(groupMapper);
    Assertions.assertNotNull(groupRepository);
    Assertions.assertNotNull(productMapper);
    Assertions.assertNotNull(productRepository);
    Assertions.assertNotNull(productViewRepository);
    Assertions.assertNotNull(productAction);
    Assertions.assertNotNull(productService);
    Assertions.assertNotNull(groupAction);
    Assertions.assertNotNull(groupService);
  }
}
