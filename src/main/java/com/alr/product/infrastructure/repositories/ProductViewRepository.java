package com.alr.product.infrastructure.repositories;

import com.alr.product.domain.entities.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, Integer> {
}
