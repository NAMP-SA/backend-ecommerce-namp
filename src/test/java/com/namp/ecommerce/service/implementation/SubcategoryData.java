package com.namp.ecommerce.service.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.dto.ProductDTO;
import com.namp.ecommerce.dto.SubcategoryDTO;
import com.namp.ecommerce.dto.SubcategoryWithProductsDTO;
import com.namp.ecommerce.model.Category;
import com.namp.ecommerce.model.Product;
import com.namp.ecommerce.model.Subcategory;

public class SubcategoryData{
    public final static List<Category> CATEGORIES = Arrays.asList(
        new Category(1L, "Bebidas con alcohol", "Bebidas para mayores de edad", new ArrayList<>()),
        new Category(2L, "Bebidas sin alcohol", "Bebidas para cualquier persona", new ArrayList<>()));


    public final static List<CategoryDTO> CATEGORIESDTO = Arrays.asList(
        new CategoryDTO(1L, "Bebidas con alcohol", "Bebidas para mayores de edad"),
        new CategoryDTO(2L, "Bebidas sin alcohol", "Bebidas para cualquier persona"));


    /*Subcategories*/
        public final static List<Subcategory> SUBCATEGORIES = Arrays.asList(
        new Subcategory(1L, "Cervezas", "Cervezas de diferentes tipos", CATEGORIES.get(0), new ArrayList<>()),
        new Subcategory(2L, "Vinos", "Vinos de diferentes tipos", CATEGORIES.get(0), new ArrayList<>()),
        new Subcategory(3L, "Refrescos", "Refrescos de diferentes tipos", CATEGORIES.get(1), new ArrayList<>()),
        new Subcategory(4L, "Jugos", "Jugos de diferentes tipos", CATEGORIES.get(1), new ArrayList<>()));


    public final static List<SubcategoryDTO> SUBCATEGORIESDTO = Arrays.asList(
        new SubcategoryDTO(1L, "Cervezas", "Cervezas de diferentes tipos", CATEGORIESDTO.get(0)),
        new SubcategoryDTO(2L, "Vinos", "Vinos de diferentes tipos", CATEGORIESDTO.get(0)),
        new SubcategoryDTO(3L, "Refrescos", "Refrescos de diferentes tipos", CATEGORIESDTO.get(1)),
        new SubcategoryDTO(4L, "Jugos", "Jugos de diferentes tipos", CATEGORIESDTO.get(1)));

    
    /* Products */
    public final static List<Product> PRODUCTS = Arrays.asList(
    new Product(1L, "Cerveza Andes", "Roja intensa", 850.0, 120, "ïmg1.jpg",
         new ArrayList<>(), 120, SUBCATEGORIES.get(0), new ArrayList<>(),  new ArrayList<>(), null),
    new Product(2L, "Cerveza Quilmes", "Rubia clásica", 800.0, 100, "img2.jpg",
         new ArrayList<>(), 100, SUBCATEGORIES.get(0), new ArrayList<>(), new ArrayList<>(), null),
 
    new Product(3L, "Vino Malbec", "Tinto reserva", 1200.0, 50, "img3.jpg",
         new ArrayList<>(), 50, SUBCATEGORIES.get(1), new ArrayList<>(), new ArrayList<>(), null),
 
    new Product(4L, "Vino Cabernet", "Seco con cuerpo", 1350.0, 30, "img4.jpg",
         new ArrayList<>(), 30, SUBCATEGORIES.get(1), new ArrayList<>(), new ArrayList<>(), null),
 
    new Product(5L, "Coca-Cola", "Botella 1.5L", 650.0, 200, "img5.jpg",
         new ArrayList<>(), 200, SUBCATEGORIES.get(2), new ArrayList<>(), new ArrayList<>(), null));
    
    public final static List<ProductDTO> PRODUCTSDTO = Arrays.asList(
        new ProductDTO(1L, "Cerveza Andes", "Roja intensa", 850.0, 120, "img1.jpg",
             SUBCATEGORIESDTO.get(0), null, 850),
        new ProductDTO(2L, "Cerveza Quilmes", "Rubia clásica", 800.0, 100, "img2.jpg",
             SUBCATEGORIESDTO.get(0), null, 800),
        new ProductDTO(3L, "Vino Malbec", "Tinto reserva", 1200.0, 50, "img3.jpg",
             SUBCATEGORIESDTO.get(1), null, 1200),
        new ProductDTO(4L, "Vino Cabernet", "Seco con cuerpo", 1350.0, 30, "img4.jpg",
             SUBCATEGORIESDTO.get(1), null, 1350),
        new ProductDTO(5L, "Coca-Cola", "Botella 1.5L", 650.0, 200, "img5.jpg",
             SUBCATEGORIESDTO.get(2), null, 650));
            
    
    /* SubcategoriesWithProducts */
    public final static List<Subcategory> SUBCATEGORIESWITHPRODUCTS = Arrays.asList(
        new Subcategory(1L, "Cervezas", "Cervezas de diferentes tipos", CATEGORIES.get(0),
            Arrays.asList(PRODUCTS.get(0), PRODUCTS.get(1))),
        new Subcategory(2L, "Vinos", "Vinos de diferentes tipos", CATEGORIES.get(0),
            Arrays.asList(PRODUCTS.get(2), PRODUCTS.get(3))),
        new Subcategory(3L, "Refrescos", "Refrescos de diferentes tipos", CATEGORIES.get(1),
            Arrays.asList(PRODUCTS.get(4))),
        new Subcategory(4L, "Jugos", "Jugos de diferentes tipos", CATEGORIES.get(1),
            new ArrayList<>()) // No hay productos asociados en tu lista PRODUCTS
    );

    public final static List<SubcategoryWithProductsDTO> SUBCATEGORIESWITHPRODUCTSDTO = Arrays.asList(
        new SubcategoryWithProductsDTO(1L, "Cervezas", "Cervezas de diferentes tipos", CATEGORIESDTO.get(0).getName(),
            Arrays.asList(PRODUCTSDTO.get(0), PRODUCTSDTO.get(1))),
        new SubcategoryWithProductsDTO(2L, "Vinos", "Vinos de diferentes tipos", CATEGORIESDTO.get(0).getName(),
            Arrays.asList(PRODUCTSDTO.get(2), PRODUCTSDTO.get(3))),
        new SubcategoryWithProductsDTO(3L, "Refrescos", "Refrescos de diferentes tipos", CATEGORIESDTO.get(1).getName(),
            Arrays.asList(PRODUCTSDTO.get(4))),
        new SubcategoryWithProductsDTO(4L, "Jugos", "Jugos de diferentes tipos", CATEGORIESDTO.get(1).getName(),
            new ArrayList<>()) // Lista vacía de productos para "Jugos"
    );

}


