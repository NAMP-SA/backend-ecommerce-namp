package com.namp.ecommerce.service.implementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namp.ecommerce.dto.ProductDTO;
import com.namp.ecommerce.dto.ProductWithItDTO;
import com.namp.ecommerce.dto.ProductWithRegisterStocksDTO;
import com.namp.ecommerce.mapper.MapperProduct;
import com.namp.ecommerce.model.Product;
import com.namp.ecommerce.repository.IProductDAO;
import com.namp.ecommerce.repository.IPromotionDAO;
import com.namp.ecommerce.repository.ISubcategoryDAO;
import com.namp.ecommerce.service.ICloudinaryService;
import com.namp.ecommerce.service.IProductService;
import com.namp.ecommerce.error.InvalidFileFormatException;
import com.namp.ecommerce.exception.DeletionException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service 
public class ProductImplementation implements IProductService{

    @Autowired
    private IProductDAO productDAO; 

    @Autowired
    private ISubcategoryDAO subcategoryDAO; 

    @Autowired
    private IPromotionDAO promotionDAO; 

    @Autowired
    private MapperProduct mapperProduct;

    @Autowired
    private ICloudinaryService cloudinaryService;

    private boolean skipFileDeletion = false;

    public void setSkipFileDeletion(boolean skipFileDeletion) {
        this.skipFileDeletion = skipFileDeletion;
    }


    @Override
    public List<ProductDTO> getProducts() {
        return productDAO.findAll()
                .stream()
                .map(mapperProduct::convertProductToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductWithItDTO> getProductsWithIt() {
        return productDAO.findAll()
                .stream()
                .map(mapperProduct::convertProductWithItToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductWithRegisterStocksDTO> getProductWithRegisterStocks(){
        return productDAO.findAll()
                .stream()
                .map(mapperProduct::convertProductWithRegisterStocksToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductsId(long id){
        Product product = productDAO.findByIdProduct(id);

        if (product == null){
            return null;
        }
        return mapperProduct.convertProductToDto(product);
    }

    @Override
    public ProductDTO save(String productJson, MultipartFile file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

        // Validación del archivo
        if (!file.isEmpty()) {
            String contentType = file.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                throw new InvalidFileFormatException("El formato del archivo no es válido. Solo se permiten JPG o PNG.");
            }

            // Generar nombre único
            String formattedDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String publicId = productDTO.getName().replaceAll("\\s+", "_").trim() + "_" + formattedDate;

            // Subir a Cloudinary y obtener la URL
            String imageUrl = cloudinaryService.uploadImage(file, publicId);

            // Setear URL en el DTO
            productDTO.setImg(imageUrl);
        }

        // Normalizar nombre
        String normalizedName = productDTO.getName().replaceAll("\\s+", " ").trim().toUpperCase();
        if (!verifyName(normalizedName)) {
            productDTO.setName(normalizedName);
            Product product = mapperProduct.convertDtoToProduct(productDTO);
            Product savedProduct = productDAO.save(product);
            return mapperProduct.convertProductToDto(savedProduct);
        }

        return null;
    }

    @Override
    public ProductDTO update(ProductDTO existingProductDTO, String productJson, MultipartFile file) throws IOException {

        Product existingProduct = productDAO.findByIdProduct(existingProductDTO.getIdProduct());
        if (existingProduct == null){
            return null;
        }

        // Convertimos el JSON en DTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);
        String normalizedName = productDTO.getName().replaceAll("\\s+", " ").trim().toUpperCase();

        // Validación del nombre
        if(verifyName(normalizedName, existingProductDTO.getIdProduct())) {
            return null;
        }

        // Seteo los campos básicos
        existingProduct.setName(normalizedName);
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());

        // Seteo subcategoría y promoción
        existingProduct.setIdSubcategory(subcategoryDAO.findByIdSubcategory(productDTO.getIdSubcategory().getIdSubcategory()));
        if (productDTO.getIdPromotion() != null) {
            existingProduct.setIdPromotion(promotionDAO.findByIdPromotion(productDTO.getIdPromotion().getIdPromotion()));
        }

        // Manejo de imagen si se cargó una nueva
        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();

            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")){
                throw new InvalidFileFormatException("El formato del archivo no es válido. Solo se permiten JPG o PNG.");
            }

            // Genero publicId y subo la nueva imagen
            String formattedDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String publicId = productDTO.getName().replaceAll("\\s+", "_").trim() + "_" + formattedDate;

            String newImageUrl = cloudinaryService.uploadImage(file, publicId);

            // Eliminar imagen anterior si existe
            if (existingProduct.getImg() != null && !existingProduct.getImg().isEmpty()) {
                cloudinaryService.deleteImageByUrl(existingProduct.getImg());
            }

            // Seteo nueva URL
            existingProduct.setImg(newImageUrl);
        }

        Product updatedProduct = productDAO.save(existingProduct);
        return mapperProduct.convertProductToDto(updatedProduct);
    }

    @Override
    public void delete(ProductDTO productDTO){
        Product product = productDAO.findByIdProduct(productDTO.getIdProduct());
        if (product == null){
            throw new EntityNotFoundException("Product not found with ID: " + productDTO.getIdProduct());
        }

        if (!skipFileDeletion && product.getImg() != null && !product.getImg().isEmpty()) {
            try {
                cloudinaryService.deleteImageByUrl(product.getImg());
            } catch (Exception e) {
                throw new DeletionException("Error al eliminar la imagen en Cloudinary del producto: " + product.getName(), e);
            }
        }

        productDAO.delete(product);
    }

    @Override
    public ProductDTO findById(long id) {
        Product product = productDAO.findByIdProduct(id);
        if (product != null){
            return mapperProduct.convertProductToDto(product);
        }
        return null;
    }

    @Override
    public boolean verifyName(String normalizedName) {
        List<Product> products = productDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");

        //Comparar el nombre de la categoria que se quiere guardar, con todos los demas sin espacio para ver si es el mismo
        for(Product product : products){
            if(name.equals(product.getName().replaceAll("\\s+", ""))){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean verifyName(String normalizedName, long idProduct) {
        List<Product> products = productDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");

        //Verifica si se repite el nombre en los demas productos, menos con el que se está actualizando
        for(Product product : products){
            if(product.getIdProduct()!=idProduct && name.equals(product.getName().replaceAll("\\s+", ""))){
                return true;
            }
        }

        return false;
    }

    @Override
    public void increaseStock(ProductDTO productDTO, int quantity) {
        Product product = productDAO.findByIdProduct(productDTO.getIdProduct());
        product.setStock(product.getStock()+quantity);
        productDAO.save(product);
    }


    @Override
    public void decreaseStock(ProductDTO productDTO, int quantity) {
        if (productDTO.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product with ID: " + productDTO.getIdProduct());
        }
        Product product = productDAO.findByIdProduct(productDTO.getIdProduct());
        product.setStock(product.getStock()-quantity);
        productDAO.save(product);
    }

    @Override
    public boolean checkStock(ProductDTO productDTO, int quantity) {
        Product product = productDAO.findByIdProduct(productDTO.getIdProduct());
        if (product.getSimulatedStock() < quantity) {
            return false;
        }

        product.setSimulatedStock(product.getSimulatedStock() - quantity);
        return true;
    }
}
