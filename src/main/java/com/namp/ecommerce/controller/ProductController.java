package com.namp.ecommerce.controller;


import com.namp.ecommerce.dto.ProductDTO;
import com.namp.ecommerce.error.InvalidFileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.namp.ecommerce.service.IProductService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api-namp")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("product")
    public ResponseEntity<?> getProducts(){
        try{
            return ResponseEntity.ok(productService.getProducts());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mostrar los productos:"+e.getMessage());
        }
    }

    @GetMapping("productWithIt")
    public ResponseEntity<?> getProductsWithIT(){
        try{
            return ResponseEntity.ok(productService.getProductsWithIt());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mostrar los productos:"+e.getMessage());
        }
    }

    @GetMapping("productWithRegisterStocks")
    public ResponseEntity<?> getProductsWithRegisterStocks(){
        try{
            return ResponseEntity.ok(productService.getProductWithRegisterStocks());

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mostrar los productos:"+e.getMessage());
        }
    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> getProductsId(@PathVariable long id){
        try{
            if (productService.getProductsId(id) == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Category with ID "+id+" not found");
            }
            return ResponseEntity.ok(productService.getProductsId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the categories:"+e.getMessage());
        }
    }

    @PostMapping("/admin/product")
    public ResponseEntity<?> createProduct(@RequestParam("product") String productJson, @RequestParam(value="file", required = false) MultipartFile file){
        // Verificar si el archivo es null o está vacío
        //Este codigo lo pusimo para formatear el error y no crear un manejador global de excepciones, pero deberiamos cambiarlo.
        if (file == null || file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La imagen es obligatoria.");
        }
        try{
            ProductDTO createdProductDTO = productService.save(productJson, file);

            if (createdProductDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This product already exists");
            }

            return ResponseEntity.ok(createdProductDTO);
        // 400 En el caso de que la imagen no cumpla el formato
        }catch (InvalidFileFormatException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the product"+e.getMessage());
        }
    }

    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id){
        try{
            ProductDTO productDTO = productService.findById(id);

            if (productDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The product does not exist");
            }

            productService.delete(productDTO);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the product: " + e.getMessage());
        }

    
    }

    @PutMapping("/admin/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestParam("product") String productJson, @RequestParam(value = "file", required = false) MultipartFile file){
        try{
            ProductDTO existingProduct = productService.findById(id);

            if (existingProduct == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The product does not exist");
            }

            ProductDTO updatedProductDTO = productService.update(existingProduct, productJson, file);

            if (updatedProductDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("The entered name already exists");
            }

            return ResponseEntity.ok(updatedProductDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating the product: " + e.getMessage());
        }
    }
    
}
