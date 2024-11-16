/*
    The only objective of this MapperUtil is to agrupate all methods that are needed in two or more mappers at the
    same time, to avoid circular dependencies. -Jeremias Antunez
*/


package com.namp.ecommerce.mapper;

import com.namp.ecommerce.dto.*;
import com.namp.ecommerce.model.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperUtil {

    /*
    ----------------------------------------------------------------------------------------------------------
                                             CATEGORY METHODS
   -----------------------------------------------------------------------------------------------------------
     */
    public CategoryDTO convertCategoryToDto(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setIdCategory(category.getIdCategory());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());

        return categoryDTO;
    }

    public CategoryWithSubcategoriesDTO convertCategoryWithSubcategoryToDto(Category category) {
        CategoryWithSubcategoriesDTO categoryWithSubcategoryDTO = new CategoryWithSubcategoriesDTO();

        categoryWithSubcategoryDTO.setIdCategory(category.getIdCategory());
        categoryWithSubcategoryDTO.setName(category.getName());
        categoryWithSubcategoryDTO.setDescription(category.getDescription());

        categoryWithSubcategoryDTO.setSubcategories(category.getSubcategories()
                .stream()
                .map(this::convertSubcategoryToDto)
                .collect(Collectors.toList()));

        return categoryWithSubcategoryDTO;
    }

    public CategoryWithSubcategoriesDTO convertCategoryIdWithSubcategoryToDto(Category category) {
        CategoryWithSubcategoriesDTO categoryIdWithSubcategoryDTO = new CategoryWithSubcategoriesDTO();

        categoryIdWithSubcategoryDTO.setIdCategory(category.getIdCategory());
        categoryIdWithSubcategoryDTO.setName(category.getName());
        categoryIdWithSubcategoryDTO.setDescription(category.getDescription());

        categoryIdWithSubcategoryDTO.setSubcategories(category.getSubcategories()
                .stream()
                .map(this::convertSubcategoryToDto)
                .collect(Collectors.toList()));

        categoryIdWithSubcategoryDTO.setSubcategoryWithProducts(category.getSubcategories()
                .stream()
                .map(this::convertSubcategoryWithProductsToDto)
                .collect(Collectors.toList()));

        return categoryIdWithSubcategoryDTO;
    }

    /*
    ----------------------------------------------------------------------------------------------------------
                                            SUBCATEGORY METHODS
   -----------------------------------------------------------------------------------------------------------
     */
    public SubcategoryDTO convertSubcategoryToDto(Subcategory subcategory){
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();

        subcategoryDTO.setIdSubcategory(subcategory.getIdSubcategory());
        subcategoryDTO.setName(subcategory.getName());
        subcategoryDTO.setDescription(subcategory.getDescription());

        subcategoryDTO.setIdCategory(this.convertCategoryToDto(subcategory.getIdCategory()));

        return subcategoryDTO;
    }

    public SubcategoryWithProductsDTO convertSubcategoryWithProductsToDto(Subcategory subcategory) {
        SubcategoryWithProductsDTO subcategoryWithProductsDTO = new SubcategoryWithProductsDTO();

        subcategoryWithProductsDTO.setIdSubcategory(subcategory.getIdSubcategory());
        subcategoryWithProductsDTO.setName(subcategory.getName());
        subcategoryWithProductsDTO.setDescription(subcategory.getDescription());

        subcategoryWithProductsDTO.setCategoryName(subcategory.getIdCategory().getName());

        subcategoryWithProductsDTO.setProducts(subcategory.getProducts()
                .stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList()));

        return subcategoryWithProductsDTO;
    }

    public SubcategoryWithProductsDTO convertSubcategoryIdWithProductsToDto(Subcategory subcategory) {
        SubcategoryWithProductsDTO subcategoryIdWithProductsDTO = new SubcategoryWithProductsDTO();

        subcategoryIdWithProductsDTO.setIdSubcategory(subcategory.getIdSubcategory());
        subcategoryIdWithProductsDTO.setName(subcategory.getName());
        subcategoryIdWithProductsDTO.setDescription(subcategory.getDescription());
        subcategoryIdWithProductsDTO.setCategoryName(subcategory.getIdCategory().getName());

        subcategoryIdWithProductsDTO.setProducts(subcategory.getProducts()
                .stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList()));

        return subcategoryIdWithProductsDTO;
    }
    /*
    ----------------------------------------------------------------------------------------------------------
                                            PRODUCT METHODS
   -----------------------------------------------------------------------------------------------------------
    */
    public ProductDTO convertProductToDto(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setImg(product.getImg());

        productDTO.setIdSubcategory(this.convertSubcategoryToDto(product.getIdSubcategory()));

        return productDTO;
    }
    
    //Este metodo esta creado porque al actualizar un RegisterStock, ya que tiraba que la subcategory era nula 
    public ProductDTO convertProductWOSubcategoryToDto(Product product){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setImg(product.getImg());
        return productDTO;
    }

    public ProductWithItDTO convertProductWithItToDto(Product product) {
        ProductWithItDTO productDTO = new ProductWithItDTO();

        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setImg(product.getImg());

        productDTO.setProductCombo(product.getProductCombo()
                .stream()
                .map(this::convertProductComboToDto)
                .collect(Collectors.toList()));

        return productDTO;
    }

    public ProductWithRegisterStocksDTO convertProductWithRegisterStocksToDto(Product product){
        ProductWithRegisterStocksDTO productDTO = new ProductWithRegisterStocksDTO();
       
        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setImg(product.getImg());

        productDTO.setRegisterStocks(product.getRegisterStocks()
            .stream()
            .map(this::convertRegisterStockToDto)
            .collect(Collectors.toList())
        );

        return productDTO;
    }
    /*
    ----------------------------------------------------------------------------------------------------------
                                         PRODUCT COMBO METHODS
   -----------------------------------------------------------------------------------------------------------
    */
    public ProductComboDTO convertProductComboToDto(ProductCombo productCombo) {
        ProductComboDTO productComboDTO = new ProductComboDTO();

        productComboDTO.setIdProductCombo(productCombo.getIdProductCombo());
        productComboDTO.setQuantity(productCombo.getQuantity());
        productComboDTO.setIdProduct(this.convertProductToDto(productCombo.getIdProduct()));
        productComboDTO.setIdCombo(this.convertComboToDto(productCombo.getIdCombo()));

        return productComboDTO;
    }
    /*
    ----------------------------------------------------------------------------------------------------------
                                             COMBO METHODS
   -----------------------------------------------------------------------------------------------------------
    */
    public ComboDTO convertComboToDto(Combo combo) {
        ComboDTO comboDTO = new ComboDTO();

        comboDTO.setIdCombo(combo.getIdCombo());
        comboDTO.setName(combo.getName());
        comboDTO.setDescription(combo.getDescription());
        comboDTO.setPrice(combo.getPrice());
        comboDTO.setImg(combo.getImg());

        return comboDTO;
    }

    public ComboWithItDTO convertComboWithItToDto(Combo combo) {
        ComboWithItDTO comboWithItDTO = new ComboWithItDTO();

        comboWithItDTO.setIdCombo(combo.getIdCombo());
        comboWithItDTO.setName(combo.getName());
        comboWithItDTO.setDescription(combo.getDescription());
        comboWithItDTO.setPrice(combo.getPrice());
        comboWithItDTO.setImg(combo.getImg());

        comboWithItDTO.setProductCombo(combo.getProductCombo()
                .stream()
                .map(this::convertProductComboToDto)
                .collect(Collectors.toList()));

        return comboWithItDTO;
    }
    /*
    ----------------------------------------------------------------------------------------------------------
                                             USERS METHODS
   -----------------------------------------------------------------------------------------------------------
    */

    public UserDTO convertUserToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setIdUser(user.getIdUser());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }

    public UserAnswerDTO convertUserToUserAnswerDTO(User user) {
        UserAnswerDTO userAnswerDTO = new UserAnswerDTO();

        userAnswerDTO.setIdUser(user.getIdUser());
        userAnswerDTO.setName(user.getName());
        userAnswerDTO.setLastname(user.getLastname());
        userAnswerDTO.setEmail(user.getEmail());
        userAnswerDTO.setAddress(user.getAddress());
        userAnswerDTO.setPhone(user.getPhone());
        userAnswerDTO.setUsername(user.getUsername());

        return userAnswerDTO;
    }

    public UserEditableDTO convertUserToUserEditableDTO(User user){
        UserEditableDTO userEditableDTO = new UserEditableDTO();

        //userEditableDTO.setIdUser(user.getIdUser());
        userEditableDTO.setName(user.getName());
        userEditableDTO.setLastname(user.getLastname());
        userEditableDTO.setUsername(user.getUsername());
        userEditableDTO.setPassword(user.getPassword());
        userEditableDTO.setEmail(user.getEmail());
        userEditableDTO.setAddress(user.getAddress());
        userEditableDTO.setPhone(user.getPhone());

        return userEditableDTO;
    }


    /*
    ----------------------------------------------------------------------------------------------------------
                                             REGISTERSTOCK METHODS
   -----------------------------------------------------------------------------------------------------------
    */

    public RegisterStockDTO convertRegisterStockToDto(RegisterStock registerStock){
        RegisterStockDTO registerStockDTO = new RegisterStockDTO();

        registerStockDTO.setIdRegisterStock(registerStock.getIdRegisterStock());
        registerStockDTO.setFechaHora(registerStock.getDateTime());
        registerStockDTO.setQuantity(registerStock.getQuantity());

        registerStockDTO.setIdProduct(this.convertProductWOSubcategoryToDto(registerStock.getIdProduct()));

        return registerStockDTO;
    }
}
