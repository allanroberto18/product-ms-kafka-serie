package com.alr.product.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ProductView")
@Immutable
public class ProductView {

  @Id
  @Column(name = "Id", updatable = false, insertable = false)
  private Integer id;

  @Column(name = "GroupId")
  private Integer groupId;

  @Column(name = "GroupName")
  private String GroupName;

  @Column(name = "ProductName")
  private String ProductName;

  @Column(name = "Price")
  private Double price;
}
