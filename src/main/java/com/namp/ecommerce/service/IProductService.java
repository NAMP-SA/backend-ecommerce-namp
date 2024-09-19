package com.namp.ecommerce.service;

import java.io.IOException;
import java.util.List;

import com.namp.ecommerce.dto.ProductDTO;
import com.namp.ecommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
    List<ProductDTO> getProducts();
    ProductDTO save(String product, MultipartFile file) throws IOException;
    ProductDTO update(ProductDTO existingProductDTO, String product, MultipartFile file) throws IOException;
    void delete(ProductDTO productDTO);
    ProductDTO findById(long id);
    boolean verifyName(String normalizedName);
    boolean verifyName(String normalizedName, long idProduct);
}