package com.ushan.product.repository;

import com.ushan.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {

//    This is a user query for get one product item form DB
    @Query(value = "SELECT * FROM Product WHERE product_id = ?1", nativeQuery = true)
    Product getProductById(Integer productId);
}
