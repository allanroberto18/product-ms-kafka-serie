package com.alr.product.domain.services;

import com.alr.product.ProductApplication;
import com.alr.product.domain.contracts.services.ProductService;
import com.alr.product.domain.entities.Group;
import com.alr.product.domain.entities.Product;
import com.alr.product.domain.entities.ProductView;
import com.alr.product.infrastructure.repositories.GroupRepository;
import com.alr.product.infrastructure.repositories.ProductRepository;
import com.alr.product.infrastructure.repositories.ProductViewRepository;
import com.alr.product.infrastructure.requests.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {ProductApplication.class})
@ActiveProfiles("test")
public class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private GroupRepository groupRepository;

  @Mock
  private ProductViewRepository productViewRepository;

  @Mock
  private BiFunction<Group, ProductDTO, Product> productMapper;

  @BeforeEach
  void setUp() {
    productService = new ProductServiceImpl(
        productMapper,
        productRepository,
        groupRepository,
        productViewRepository
    );
  }

  @Test
  public void save_withProductDTO_mustReturnProduct() {
    String name = "Product Name";
    Integer groupId = 1;
    String groupName = "Group Name";
    Double price = 1D;

    ProductDTO productDTO = getProductDTO(name, groupId, price);
    Group group = getGroup(groupId, groupName);

    Product productExpected = getProduct(group, name, price);

    Mockito.when(productMapper.apply(group, productDTO)).thenReturn(productExpected);
    Mockito.when(productRepository.save(productExpected)).thenReturn(productExpected);
    Mockito.when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

    Product product = productService.save(productDTO);

    makeProductAssertions(productExpected, product);
  }

  @Test
  public void save_withProductDTO_mustThrowException() {
    Assertions.assertThrows(RuntimeException.class, () -> {
      String name = "Product Name";
      Integer groupId = 1;
      Double price = 1D;

      ProductDTO productDTO = getProductDTO(name, groupId, price);
      Mockito.when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

      productService.save(productDTO);
    });
  }

  @Test
  public void save_withIdAndProductDTO_mustReturnProduct() {
    Integer id = 1;
    String name = "Product Name";
    Integer groupId = 1;
    String groupName = "Group Name";
    Double price = 1D;

    ProductDTO productDTO = getProductDTO(name, groupId, price);
    Group group = getGroup(groupId, groupName);
    Product productExpected = getProduct(id, group, name, price);

    Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(productExpected));
    Mockito.when(productRepository.save(productExpected)).thenReturn(productExpected);

    Product product = productService.save(id, productDTO);

    makeProductAssertions(productExpected, product);
  }

  @Test
  public void save_withIdAndProductDTO_mustThrowException() {
    Assertions.assertThrows(RuntimeException.class, () -> {
      Integer id = 1;
      String name = "Product Name";
      Integer groupId = 1;
      Integer otherGroupId = 2;
      String groupName = "Group Name";
      Double price = 1D;

      ProductDTO productDTO = getProductDTO(name, otherGroupId, price);
      Group group = getGroup(groupId, groupName);
      Product productExpected = getProduct(id, group, name, price);

      Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(productExpected));
      Mockito.when(productRepository.save(productExpected)).thenReturn(productExpected);
      Mockito.when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

      productService.save(id, productDTO);
    });
  }

  @Test
  public void save_withIdAndProductDTO_WithNotFoundProduct_mustReturnProduct() {
    Integer id = 1;
    String name = "Product Name";
    Integer groupId = 1;
    String groupName = "Group Name";
    Double price = 1D;

    ProductDTO productDTO = getProductDTO(name, groupId, price);
    Group group = getGroup(groupId, groupName);
    Product productExpected = getProduct(id, group, name, price);

    Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());
    Mockito.when(productRepository.save(productExpected)).thenReturn(productExpected);
    Mockito.when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    Mockito.when(productMapper.apply(group, productDTO)).thenReturn(productExpected);

    Product product = productService.save(id, productDTO);

    makeProductAssertions(productExpected, product);
  }

  @Test
  public void find_WithValidId_MustReturnAProductView() {
    Integer id = 1;
    String name = "Product Name";
    Integer groupId = 1;
    String groupName = "Group Name";
    Double price = 1D;

    ProductView productExpected = getProductView(id, groupId, name, groupName, price);

    Mockito.when(productViewRepository.findById(id)).thenReturn(Optional.of(productExpected));

    Optional<ProductView> product = productService.find(id);

    makeProductViewAssertions(productExpected, product);
  }

  @Test
  public void find_WithInvalidId_MustReturnEmpty() {
    Integer id = 1;

    Mockito.when(productViewRepository.findById(id)).thenReturn(Optional.empty());

    Optional<ProductView> product = productService.find(id);

    Assertions.assertFalse(product.isPresent());
  }

  @Test
  public void all_NoArguments_ReturnListOfProduct() {
    Integer id = 1;
    String name = "Product Name";
    Integer groupId = 1;
    String groupName = "Group Name";
    Double price = 1D;

    ProductView product = getProductView(id, groupId, name, groupName, price);
    List<ProductView> productsExpected = List.of(product);

    Mockito.when(productViewRepository.findAll()).thenReturn(productsExpected);

    List<ProductView> products = productService.all();

    Assertions.assertEquals(productsExpected.size(), products.size());
  }

  private void makeProductViewAssertions(ProductView productExpected, Optional<ProductView> product) {
    Assertions.assertTrue(product.isPresent());

    makeProductViewAssertions(productExpected, product.get());
  }

  private void makeProductViewAssertions(ProductView productExpected, ProductView product) {
    Assertions.assertEquals(productExpected.getId(), product.getId());
    Assertions.assertEquals(productExpected.getGroupId(), product.getGroupId());
    Assertions.assertEquals(productExpected.getProductName(), product.getProductName());
    Assertions.assertEquals(productExpected.getGroupName(), product.getGroupName());
    Assertions.assertEquals(productExpected.getPrice(), product.getPrice());
  }

  private void makeProductAssertions(Product productExpected, Product product) {
    Assertions.assertNotNull(product);
    Assertions.assertEquals(productExpected.getGroup().getName(), product.getGroup().getName());
    Assertions.assertEquals(productExpected.getName(), product.getName());
    Assertions.assertEquals(productExpected.getPrice(), product.getPrice());
  }

  private ProductView getProductView(Integer id, Integer groupId, String name, String groupName, Double price) {
    return ProductView.builder()
        .id(id)
        .groupId(groupId)
        .GroupName(groupName)
        .ProductName(name)
        .price(price)
        .build();
  }

  private ProductDTO getProductDTO(String name, Integer groupId, Double price) {
    return ProductDTO.builder()
        .name(name)
        .groupId(groupId)
        .price(price)
        .build();
  }

  private Group getGroup(Integer groupId, String groupName) {
    return Group.builder()
        .id(groupId)
        .name(groupName)
        .build();
  }

  private Product getProduct(Integer id, Group group, String name, Double price) {
    Product product = getProduct(group, name, price);
    product.setId(id);

    return product;
  }

  private Product getProduct(Group group, String name, Double price) {
    return Product.builder()
        .name(name)
        .group(group)
        .price(price)
        .build();
  }
}
