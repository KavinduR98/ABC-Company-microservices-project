package com.ushan.product.service;

import com.ushan.product.dto.ProductDTO;
import com.ushan.product.model.Product;
import com.ushan.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts(){
        List<Product> productList = productRepository.findAll();
        return modelMapper.map(productList,new TypeToken<List<ProductDTO>>(){}.getType());
    }

    public ProductDTO saveProduct(ProductDTO productDTO){
        productRepository.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }

    public ProductDTO updateProduct(ProductDTO productDTO){
        productRepository.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }

    public String deleteProduct(Integer productId){
        productRepository.deleteById(productId);
        return "Product Deleted!";
    }

    public ProductDTO getProductById(Integer productId){
        Product product = productRepository.getProductById(productId);
        return  modelMapper.map(product, ProductDTO.class);
    }


}
