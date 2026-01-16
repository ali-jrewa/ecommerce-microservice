package com.allosh.ecommerce.controller;

import com.allosh.ecommerce.product.ProductPurchaseRequest;
import com.allosh.ecommerce.product.ProductPurchaseResponse;
import com.allosh.ecommerce.product.ProductRequest;
import com.allosh.ecommerce.product.ProductResponse;
import com.example.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }
    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> createProduct(
            @RequestBody List<ProductPurchaseRequest>  request){
        return ResponseEntity.ok(productService.purchaseProducts(request));
    }
    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Integer productId){
        return ResponseEntity.ok(productService.findById(productId));
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findALl(){
        return ResponseEntity.ok(productService.findAllProducts());
    }
}
