package com.alr.product.infrastructure.controllers;

import com.alr.product.application.actions.ProductAction;
import com.alr.product.domain.entities.ProductView;
import com.alr.product.infrastructure.requests.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

  private MockMvc mvc;

  @Mock
  private ProductAction productAction;

  @InjectMocks
  private ProductController productController;

  private String basePath = "/api/product";
  private JacksonTester<ProductView> jsonProductView;
  private JacksonTester<List<ProductView>> jsonProductsView;
  private JacksonTester<ProductDTO> jsonProductDTO;

  @BeforeEach
  void setUp() {
    JacksonTester.initFields(this, new ObjectMapper());
    mvc = MockMvcBuilders.standaloneSetup(productController)
        .setControllerAdvice(new RestControllerExceptionHandler())
        .build();
  }

  @Test
  public void find_WithId_MustReturnProduct() throws Exception {
    int id = 1;
    int groupId = 1;
    String name = "product 1";
    String groupName = "product 1";
    Double price = 1D;
    String path = String.format("%s/find/%d", basePath, id);
    ProductView product = new ProductView(id, groupId, groupName, name, price);
    Mockito.when(productAction.getProduct(id)).thenReturn(Optional.of(product));

    ResultActions resultActions = mvc.perform(
        get(path).contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    resultActions.andExpect(status().isOk());
    Assertions.assertEquals(
        resultActions.andReturn().getResponse().getContentAsString(),
        jsonProductView.write(product).getJson()
    );
  }

  @Test
  public void find_WithId_MustReturnNotFound() throws Exception {
    int id = 1;
    String path = String.format("%s/find/%d", basePath, id);
    Mockito.when(productAction.getProduct(id)).thenReturn(Optional.empty());

    ResultActions resultActions = mvc.perform(
        get(path).contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    resultActions.andExpect(status().isNotFound());
  }

  @Test
  public void all_NoArguments_MustReturnListOfProduct() throws Exception {
    String path = String.format("%s/all", basePath);
    List<ProductView> products = List.of(
        new ProductView(1, 1, "Group Name", "product 1", 1D),
        new ProductView(2, 1, "Group Name", "product 2", 1D)
    );

    Mockito.when(productAction.getProducts()).thenReturn(products);

    ResultActions resultActions = mvc.perform(
        get(path).contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    resultActions.andExpect(status().isOk());
    Assertions.assertEquals(
        resultActions.andReturn().getResponse().getContentAsString(),
        jsonProductsView.write(products).getJson()
    );
  }

  @Test
  public void save_WithProductDTO_MustReturnProduct() throws Exception {
    String path = String.format("%s/save", basePath);

    String name = "product 1";
    int groupId = 1;
    Double price = 1D;

    ResultActions resultActions = performPostRequest(path, name, groupId, price);

    resultActions.andExpect(status().isCreated());
  }

  @Test
  public void save_WithIdAndProductDTONoName_MustReturnBadRequest() throws Exception {
    String path = String.format("%s/save", basePath);
    String name = "";
    int groupId = 1;
    Double price = 1D;

    ResultActions resultActions = performPostRequest(path, name, groupId, price);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void save_WithIdAndProductDTONoGroupId_MustReturnBadRequest() throws Exception {
    String path = String.format("%s/save", basePath);
    String name = "Product 1";
    Integer groupId = null;
    Double price = 1D;

    ResultActions resultActions = performPostRequest(path, name, groupId, price);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void save_WithIdAndProductDTONoPrice_MustReturnBadRequest() throws Exception {
    int groupId = 1;
    String path = String.format("%s/save", basePath);
    String name = "product 1";
    Double price = null;

    ResultActions resultActions = performPostRequest(path, name, groupId, price);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void update_WithIdAndProductDTO_MustReturnProduct() throws Exception {
    int id = 1;

    String path = String.format("%s/update/%d", basePath, id);
    int groupId = 1;
    String name = "product 1";
    Double price = 1D;

    ResultActions resultActions = performPutRequest(path, name, groupId, price);

    resultActions.andExpect(status().isAccepted());
  }

  @Test
  public void update_WithIdAndProductDTONoName_MustReturnBadRequest() throws Exception {
    int id = 1;
    String path = String.format("%s/update/%d", basePath, id);
    int groupId = 1;
    String name = "";
    Double price = 1D;

    ResultActions resultActions = performPutRequest(path, name, groupId, price);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void update_WithIdAndProductDTONoGroupId_MustReturnBadRequest() throws Exception {
    int id = 1;
    Integer groupId = null;
    String path = String.format("%s/update/%d", basePath, id);
    String name = "product 1";
    Double price = 1D;

    ResultActions resultActions = performPutRequest(path, name, groupId, price);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void update_WithIdAndProductDTONoPrice_MustReturnBadRequest() throws Exception {
    int id = 1;
    int groupId = 1;
    String path = String.format("%s/update/%d", basePath, id);
    String name = "product 1";
    Double price = null;

    ResultActions resultActions = performPutRequest(path, name, groupId, price);

    resultActions.andExpect(status().isBadRequest());
  }

  private ResultActions performPostRequest(String path, String name, Integer groupId, Double price) throws Exception {
    ProductDTO productDTO = getProductDTO(name, groupId, price);

    String jsonRequest = jsonProductDTO.write(productDTO).getJson();
    return mvc.perform(
        post(path)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonRequest)
    );
  }

  private ResultActions performPutRequest(String path, String name, Integer groupId, Double price) throws Exception {
    ProductDTO productDTO = getProductDTO(name, groupId, price);

    String jsonRequest = jsonProductDTO.write(productDTO).getJson();
    return mvc.perform(
        put(path)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonRequest)
    );
  }

  private ProductDTO getProductDTO(String name, Integer groupId, Double price) {
    return ProductDTO.builder()
        .name(name)
        .price(price)
        .groupId(groupId)
        .build();
  }
}
