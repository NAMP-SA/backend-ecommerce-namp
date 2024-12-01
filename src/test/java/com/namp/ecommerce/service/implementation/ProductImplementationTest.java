
package com.namp.ecommerce.service.implementation;

import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.dto.ProductDTO;
import com.namp.ecommerce.dto.SubcategoryDTO;
import com.namp.ecommerce.service.IProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.namp.ecommerce.model.Product;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.multipart.MultipartFile;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductImplementationTest {


    private static Validator validator;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test public void testPriceNotExceedMaxLimit_Equal() {
        // Creo objeto
        Product product = new Product();
        product.setPrice(500000);

        // Valido el objeto
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "price");

        // Aqui no debe existir una violacion de validacion porque se cumple el limite max del precio
        assertTrue(violations.isEmpty(), "No debería haber una violación de validación");
    }

    @Test public void testPriceExceedMaxLimit() {
        // Creo objeto
        Product product = new Product();
        product.setPrice(500001);

        // Valido el objeto
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "price");

        // Aqui debe existir una violacion de validacion porque no se cumple el limite max del precio
        assertEquals(1, violations.size(), "Mensaje de violacion de validacion");
    }

    @Test public void testPriceNotExceedMaxLimit_Under() {
        // Creo objeto
        Product product = new Product();
        product.setPrice(499999);

        // Valido el objeto
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "price");

        // Aqui no debe existir una violacion de validacion porque se cumple el limite max del precio
        assertTrue(violations.isEmpty(), "No debería haber una violación de validación");
    }

    @Test public void testPriceNotExceedMinLimit_Upper() {
        // Creo objeto
        Product product = new Product();
        product.setPrice(1);

        // Valido el objeto
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "price");

        // Aqui no debe existir una violacion de validacion porque se cumple el limite min del precio
        assertTrue(violations.isEmpty(), "No debería haber una violación de validación");
    }

    @Test public void testPriceNotExceedMinLimit_Equal() {
        // Creo objeto
        Product product = new Product();
        product.setPrice(0);

        // Valido el objeto
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "price");

        // Aqui no debe existir una violacion de validacion porque se cumple el limite min del precio
        assertTrue(violations.isEmpty(), "No debería haber una violación de validación");
    }

    @Test public void testPriceNotExceedMinLimit_Under() {
        // Creo objeto
        Product product = new Product();
        product.setPrice(-1);
        // Valido el objeto
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "price");

        // Aqui debe existir una violacion de validacion porque no se cumple el limite min del precio
        assertEquals(1, violations.size(), "Mensaje de violacion de validacion");
    }

    @Test public void testNameNotEmpty_Alphanumeric() {
        // Creo objeto
        Product product = new Product();
        product.setName("Producto123");

        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "name");

        assertTrue(violations.isEmpty(), "No debería haber una violación de validación");
    }

    @Test public void testNameNotAlphanumeric() {
        // Creo objeto
        Product product = new Product();
        product.setName("Producto!!");
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "name");

        assertEquals(1, violations.size(), "Mensaje de violacion de validacion");
    }

    @Test public void testNameEmpty() {
        // Creo objeto
        Product product = new Product();
        product.setName("  ");

        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "name");

        assertEquals(1, violations.size(), "Mensaje de violacion de validacion");
    }

    @Test public void testDescriptionNotExceedMaxLimit_Equal() {
        // Creo objeto
        Product product = new Product();
        product.setDescription("Este Malbec es intenso y elegante, con notas de ciruela, cerezas maduras y sutiles toques de roble. " +
                "Ideal para carnes rojas, quesos añejos o disfrutar solo. Un placer argentino en cada sorbo.");

        // Valido el objeto
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "description");

        // Aqui no debe existir una violacion de validacion porque se cumple el limite max del precio
        assertTrue(violations.isEmpty(), "No debería haber una violación de validación");
    }

    @Test public void testDescriptionNotExceedMaxLimit_Under() {
        // Creo objeto
        Product product = new Product();
        product.setDescription("Malbec de carácter único, destaca por aromas de frutas rojas, especias y un suave toque de vainilla. " +
                "Perfecto para acompañar carnes, pastas o disfrutar en momentos especiales. Pura esencia argentina.");

        // Valido el objeto
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "description");

        // Aqui no debe existir una violacion de validacion porque se cumple el limite max del precio
        assertTrue(violations.isEmpty(), "No debería haber una violación de validación");
    }

    @Test public void testDescriptionNotExceedMaxLimit_Upper() {
        // Creo objeto
        Product product = new Product();
        product.setDescription("Malbec vibrante y sofisticado, con notas de frutas negras, ciruela y un toque de chocolate. Su paso por barricas aporta elegancia. " +
                "Excelente con asados, quesos intensos o en reuniones especiales para..");

        // Valido el objeto
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, "description");

        assertEquals(1, violations.size(), "Mensaje de violacion de validacion");
    }

    @Test
    public void testStockNotExceedMaxLimit_Equal() throws Exception {

        ProductDTO productDTO = new ProductDTO(1L, "Prueba sistemas", "Descripción prueba sistemas", 1, 100000, "prueba.jpg", null, null);

        String productJson = """
                {
                    "name": "Prueba sistemas",
                    "description": "Descripción prueba sistemas",
                    "price": 1,
                    "stock": 100000,
                    "img": "prueba1.jpg"
                    }
                }
            """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "prueba1.jpg",
                "image/jpeg",
                "Contenido de prueba".getBytes()
        );

        when(productService.save(productJson, file)).thenReturn(productDTO);

        ProductDTO result = productService.save(productJson, file);

        assertEquals(result, productDTO);
    }

    @Test
    public void testStockNotExceedMaxLimit_Under() throws Exception {

        ProductDTO productDTO = new ProductDTO(1L, "Prueba sistemas", "Descripción prueba sistemas", 1, 99999, "prueba.jpg", null, null);

        String productJson = """
                {
                    "name": "Prueba sistemas",
                    "description": "Descripción prueba sistemas",
                    "price": 1,
                    "stock": 99999,
                    "img": "prueba1.jpg"
                }
            """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "prueba1.jpg",
                "image/jpeg",
                "Contenido de prueba".getBytes()
        );

        when(productService.save(productJson, file)).thenReturn(productDTO);

        ProductDTO result = productService.save(productJson, file);

        assertEquals(result, productDTO);

    }

    @Test
    public void testStockExceedMaxLimit_Upper() throws Exception {
        String productJson = """
                {
                    "name": "Prueba sistemas",
                    "description": "Descripción prueba sistemas",
                    "price": 1,
                    "stock": 100001,
                    "img": "prueba1.jpg", 
                    "idSubcategory": {
                        "idSubcategory": 1,
                        "name": "Subcategoria Test"
                    }
                }
            """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "prueba1.jpg",
                "image/jpeg",
                "Contenido de prueba".getBytes()
        );

        when(productService.save(productJson, file)).thenReturn(null);

        ProductDTO result = productService.save(productJson, file);

        assertNull(result);

    }

    @Test
    public void testStockNotExceedMinLimit_Equal() throws Exception {
        ProductDTO productDTO = new ProductDTO(1L, "Prueba sistemas", "Descripción prueba sistemas", 1, 0, "prueba.jpg", null, null);

        String productJson = """
                {
                    "name": "Prueba sistemas",
                    "description": "Descripción prueba sistemas",
                    "price": 1,
                    "stock": 0,
                    "img": "prueba1.jpg"
                }
            """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "prueba1.jpg",
                "image/jpeg",
                "Contenido de prueba".getBytes()
        );

        when(productService.save(productJson, file)).thenReturn(productDTO);

        ProductDTO result = productService.save(productJson, file);

        assertEquals(result, productDTO);

    }

    @Test
    public void testStockNotExceedMinLimit_Upper() throws Exception {
        String productJson = """
                {
                    "name": "Prueba sistemas",
                    "description": "Descripción prueba sistemas",
                    "price": 1,
                    "stock": 1,
                    "img": "prueba1.jpg", 
                    "idSubcategory": {
                        "idSubcategory": 1,
                        "name": "Subcategoria Test"
                    }
                }
            """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "prueba1.jpg",
                "image/jpeg",
                "Contenido de prueba".getBytes()
        );

        when(productService.save(productJson, file)).thenReturn(null);

        ProductDTO result = productService.save(productJson, file);

        assertNull(result);
    }

    @Test
    public void testStockExceedMinLimit_Under() throws Exception {

        String productJson = """
                {
                    "name": "Prueba sistemas",
                    "description": "Descripción prueba sistemas",
                    "price": 1,
                    "stock": -1,
                    "img": "prueba1.jpg", 
                    "idSubcategory": {
                        "idSubcategory": 1,
                        "name": "Subcategoria Test"
                    }
                }
            """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "prueba1.jpg",
                "image/jpeg",
                "Contenido de prueba".getBytes()
        );

        when(productService.save(productJson, file)).thenReturn(null);

        ProductDTO result = productService.save(productJson, file);

        assertNull(result);

    }

    @Test public void registerProduct() throws Exception {

        ProductDTO productDTO = new ProductDTO(1L,"Producto1", "Descripcion de prueba", 199.99, 50, "prueba.jpg", null, null);

        String productJson = """
        {
            "name": "Producto1",
            "description": "Descripción de prueba",
            "price": 199.99,
            "stock": 50
        }
        """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "prueba1.jpg",
                "image/jpeg",
                "Contenido de prueba".getBytes()
        );

        when(productService.save(productJson, file)).thenReturn(productDTO);

        ProductDTO result = productService.save(productJson, file);

        assertEquals(result, productDTO);

    }

    @Test public void registerProduct_Exist() throws Exception {

        String productJson = """
        {
            "name": "Producto2",
            "description": "Descripción de prueba",
            "price": 199.99,
            "stock": 50
        }
        """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "prueba1.jpg",
                "image/jpeg",
                "Contenido de prueba".getBytes()
        );

        when(productService.save(productJson, file)).thenReturn(null);

        ProductDTO result = productService.save(productJson, file);

        assertNull(result);

    }
}

/*
 LISTADO DE NUEVOS CAMBIOS

    1. @Max -> Price
    2. @NotBlank -> name para que no acepte ninguna cadena con espacios.
    3. @Size -> en la descripcion para que acepte max 200 caracteres
    4. @Max -> stock para los 100.000
*/
