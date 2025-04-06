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

        if(product.getIdPromotion() != null){
            productDTO.setIdPromotion(this.convertPromotionToDTO(product.getIdPromotion()));
        }

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

    public ProductWithDoDTO convertProductWithDoToDto(Product product){
        ProductWithDoDTO productWithDoDTO = new ProductWithDoDTO();

        productWithDoDTO.setIdProduct(product.getIdProduct());
        productWithDoDTO.setName(product.getName());
        productWithDoDTO.setDescription(product.getDescription());
        productWithDoDTO.setPrice(product.getPrice());
        productWithDoDTO.setStock(product.getStock());
        productWithDoDTO.setImg(product.getImg());

        productWithDoDTO.setOrderDetail(product.getOrderDetail()
                .stream()
                .map(this::convertOrderDetailToDto)
                .collect(Collectors.toList()));

        return productWithDoDTO;
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
    public ComboWithDoDTO convertComboWithDoTDoDto(Combo combo){
        ComboWithDoDTO comboWithDoDTO = new ComboWithDoDTO();

        comboWithDoDTO.setIdCombo(combo.getIdCombo());
        comboWithDoDTO.setName(combo.getName());
        comboWithDoDTO.setDescription(combo.getDescription());
        comboWithDoDTO.setPrice(combo.getPrice());
        comboWithDoDTO.setImg(combo.getImg());

        comboWithDoDTO.setOrderDetail(combo.getOrderDetail()
                .stream()
                .map(this:: convertOrderDetailToDto)
                .collect(Collectors.toList()));

        return comboWithDoDTO;
    }

    /*
    ----------------------------------------------------------------------------------------------------------
                                             State METHODS
   -----------------------------------------------------------------------------------------------------------
    */
    public StateDTO convertStateToDTO(State state) {
        StateDTO stateDTO = new StateDTO();

        stateDTO.setIdState(state.getIdState());
        stateDTO.setName(state.getName());

        return stateDTO;
    }

    public StateWithOrdersDTO convertStateWithOrderToDTO(State state){
        StateWithOrdersDTO stateWithOrderDTO = new StateWithOrdersDTO();

        stateWithOrderDTO.setIdState(state.getIdState());
        stateWithOrderDTO.setName(state.getName());

        stateWithOrderDTO.setOrdes(state.getOrders()
            .stream()
            .map(this::convertOrderToDTO)
            .collect(Collectors.toList()));

        return stateWithOrderDTO;
    }

    public StateWithOrdersDTO convertStateIdWithOrderDTO(State state){
        StateWithOrdersDTO stateIdWithOrderDTO = new StateWithOrdersDTO();

        stateIdWithOrderDTO.setIdState(state.getIdState());
        stateIdWithOrderDTO.setName(state.getName());

        stateIdWithOrderDTO.setOrdes(state.getOrders()
            .stream()
            .map(this::convertOrderToDTO)
            .collect(Collectors.toList()));

        return stateIdWithOrderDTO;
    }


    /*
    ----------------------------------------------------------------------------------------------------------
                                             Order METHODS
   -----------------------------------------------------------------------------------------------------------
    */
    public OrderDTO convertOrderToDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setIdOrder(order.getIdOrder());
        //orderDTO.setTotal(order.getTotal());
        orderDTO.setDateTime(order.getDateTime());
        orderDTO.setIdState(this.convertStateToDTO(order.getIdState()));
        orderDTO.setIdUser(this.convertUserToUserDTO(order.getIdUser()));

        return orderDTO;
    }

    //Este metodo tambien es para que no tire error porque no existe la clave foranea
    public OrderDTO convertOrderWoStateToDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setIdOrder(order.getIdOrder());
        orderDTO.setDateTime(order.getDateTime());
        orderDTO.setIdUser(this.convertUserToUserDTO(order.getIdUser()));
        return orderDTO;
    }

    public OrderWithDoDTO convertOrderWithDoToDto(Order order){
        OrderWithDoDTO orderWithDoDTO = new OrderWithDoDTO();

        orderWithDoDTO.setIdOrder(order.getIdOrder());
        //orderWithDoDTO.setTotal(order.getTotal());
        orderWithDoDTO.setDateTime(order.getDateTime());
        orderWithDoDTO.setIdState(this.convertStateToDTO(order.getIdState()));
        orderWithDoDTO.setIdUser(this.convertUserToUserDTO(order.getIdUser()));
        orderWithDoDTO.setOrderDetail(order.getOrderDetail()
                    .stream()
                    .map(this::convertOrderDetailToDto)
                    .collect(Collectors.toList()));

        return orderWithDoDTO;
    }


    /*
    ----------------------------------------------------------------------------------------------------------
                                             Detail Order METHODS
   -----------------------------------------------------------------------------------------------------------
    */

    public OrderDetailDTO convertOrderDetailToDto(OrderDetail orderDetail){
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();


        orderDetailDTO.setIdOrderDetail(orderDetail.getIdDetailOrder());
        orderDetailDTO.setSubTotal(orderDetail.getSubTotal());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());

        if (orderDetail.getIdCombo() == null){
            orderDetailDTO.setIdProduct(this.convertProductWOSubcategoryToDto(orderDetail.getIdProduct()));
        }else{
            orderDetailDTO.setIdCombo(this.convertComboToDto(orderDetail.getIdCombo()));
        }

        orderDetailDTO.setIdOrder(this.convertOrderWoStateToDTO(orderDetail.getIdOrder()));
        return orderDetailDTO;
    }


     /*
    ----------------------------------------------------------------------------------------------------------
                                             Promotion METHODS
   -----------------------------------------------------------------------------------------------------------
    */

    public PromotionDTO convertPromotionToDTO(Promotion promotion){
        PromotionDTO promotionDTO = new PromotionDTO();

        promotionDTO.setIdPromotion(promotion.getIdPromotion());
        promotionDTO.setName(promotion.getName());
        promotionDTO.setDiscount(promotion.getDiscount());
        promotionDTO.setDateTimeStart(promotion.getDateTimeStart());
        promotionDTO.setDateTimeEnd(promotion.getDateTimeEnd());
        promotionDTO.setInEffect(promotion.isInEffect());


        return promotionDTO;
    }

    public PromotionWithProductsDTO convertPromotionWithProductsDto(Promotion promotion){
        PromotionWithProductsDTO promotionWithProductsDTO = new PromotionWithProductsDTO();

        promotionWithProductsDTO.setIdPromotion(promotion.getIdPromotion());
        promotionWithProductsDTO.setName(promotion.getName());
        promotionWithProductsDTO.setDiscount(promotion.getDiscount());
        promotionWithProductsDTO.setDateHourStart(promotion.getDateTimeStart());
        promotionWithProductsDTO.setDateHourEnd(promotion.getDateTimeEnd());
        promotionWithProductsDTO.setInEffect(promotion.isInEffect());


        promotionWithProductsDTO.setProducts(promotion.getProducts()
                .stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList()));

        return promotionWithProductsDTO;
    }

    public PromotionWithProductsDTO convertPromotionIdWithProductsDto(Promotion promotion){
        PromotionWithProductsDTO promotionIdWithProductsDTO = new PromotionWithProductsDTO();

        promotionIdWithProductsDTO.setIdPromotion(promotion.getIdPromotion());
        promotionIdWithProductsDTO.setName(promotion.getName());
        promotionIdWithProductsDTO.setDiscount(promotion.getDiscount());
        promotionIdWithProductsDTO.setDateHourStart(promotion.getDateTimeStart());
        promotionIdWithProductsDTO.setDateHourEnd(promotion.getDateTimeEnd());
        promotionIdWithProductsDTO.setInEffect(promotion.isInEffect());

        promotionIdWithProductsDTO.setProducts(promotion.getProducts()
                .stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList()));

        return promotionIdWithProductsDTO;
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
        userAnswerDTO.setRole(user.getRole());

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


    public UserWithReviewDTO convertUserWithReviewToDTO (User user){
        UserWithReviewDTO userWithReviewDTO = new UserWithReviewDTO();

        userWithReviewDTO.setIdUser(user.getIdUser());
        userWithReviewDTO.setName(user.getName());
        userWithReviewDTO.setLastname(user.getLastname());
        userWithReviewDTO.setEmail(user.getEmail());
        userWithReviewDTO.setAddress(user.getAddress());
        userWithReviewDTO.setPhone(user.getPhone());
        userWithReviewDTO.setUsername(user.getUsername());
        userWithReviewDTO.setPassword(user.getPassword());

        userWithReviewDTO.setReviews(user.getReviews()
                .stream()
                .map(this::convertReviewToDto)
                .collect(Collectors.toList()));

        return userWithReviewDTO;
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

        /*
    ----------------------------------------------------------------------------------------------------------
                                             Review Methods
   -----------------------------------------------------------------------------------------------------------
    */

    public ReviewDTO convertReviewToDto(Review review){
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setIdReview(review.getIdReview());
        reviewDTO.setMessage(review.getMessage());
        reviewDTO.setSubject(review.getSubject());

        reviewDTO.setIdUser(this.convertUserToUserDTO(review.getIdUser()));

        return reviewDTO;
    }
}
