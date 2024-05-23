package com.protech.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protech.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>  {

}
