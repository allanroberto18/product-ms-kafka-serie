package com.alr.product.infrastructure.controllers;

import com.alr.product.application.actions.ProductAction;
import com.alr.product.domain.entities.Product;
import com.alr.product.domain.entities.ProductView;
import com.alr.product.infrastructure.requests.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(path = "/api/product")
public class ProductController {

  private ProductAction productAction;

  public ProductController(ProductAction productAction) {
    this.productAction = productAction;
  }

  @GetMapping(path = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductView> getGroup(@PathVariable("id") @NotBlank Integer id) {
    return productAction.getProduct(id)
        .map(group -> ResponseEntity.ok(group))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
  }

  @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ProductView>> getGroups() {
    return ResponseEntity.ok(
        productAction.getProducts()
    );
  }

  @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> saveCustomer(@RequestBody @Valid ProductDTO productDTO) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(productAction.save(productDTO));
  }

  @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> saveCustomer(@PathVariable("id") @NotBlank Integer id, @RequestBody @Valid ProductDTO productDTO) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(productAction.save(id, productDTO));
  }
}
