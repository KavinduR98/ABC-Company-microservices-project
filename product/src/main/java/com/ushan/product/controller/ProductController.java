package com.ushan.product.controller;

import com.ushan.product.dto.ProductDTO;
import com.ushan.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/test")
    public String test(){
        return "Hello User!";
    }

    @GetMapping(path = "/get_products")
    public List<ProductDTO> getProducts(){
        return productService.getAllProducts();
    }

    @PostMapping(path = "/save_product")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO){
        System.out.println("Received ProductDTO: " + productDTO); // Log the received data
        return productService.saveProduct(productDTO);
    }

    @PutMapping(path = "/update_product")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }

    @DeleteMapping(path = "/delete_product/{productId}")
    public String deleteProduct(@PathVariable Integer productId){
        return productService.deleteProduct(productId);
    }

    @GetMapping(path = "/get_product/{productId}")
    public ProductDTO getProduct(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }

}
