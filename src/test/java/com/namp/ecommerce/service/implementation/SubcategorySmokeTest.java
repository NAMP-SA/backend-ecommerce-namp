package com.namp.ecommerce.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namp.ecommerce.dto.SubcategoryDTO;
import com.namp.ecommerce.model.Subcategory;
import com.namp.ecommerce.service.ISubcategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest //Carga todo el contexto de Spring para realizar los Smoke Test 
@AutoConfigureMockMvc // Habilita e inyecta el objeto MockMvc para simular requests HTTP 
public class SubcategorySmokeTest {

    @Autowired
    private MockMvc mockMvc; // simular peticionse HTTP sin necesidad del servidor real

    @MockBean
    private ISubcategoryService subcategoryService; 

    private String BASE_URL = "/api-namp"; 
    private ObjectMapper objectMapper = new ObjectMapper(); // Se usa para convertir objetos Java a JSON

    @Test
    void getSubcategories__returns200() throws Exception {

        // Simula que el service devuelve una lista DTO al llamar a getSubcategories
        when(subcategoryService.getSubcategories()).thenReturn(SubcategoryData.SUBCATEGORIESDTO);

        // Simulamos una peticion GET y espera un 200 OK
        mockMvc.perform(get(BASE_URL + "/subcategory"))
                .andExpect(status().isOk());  //Indicamos la reespuesta esperada para la solicitud establecida, 200 OK en este caso. 
    }

    @Test
    void getSubcategoriesWithProducts__returns200() throws Exception {
        when(subcategoryService.getSubcategoriesWithProducts()).thenReturn(SubcategoryData.SUBCATEGORIESWITHPRODUCTSDTO);
        
        //Simula una peticion GET y espera un 200 OK para subcategoryWithProducts 
        mockMvc.perform(get(BASE_URL + "/subcategoryWithProducts"))
                .andExpect(status().isOk());
    }

    @Test
    void getSubcategoryWithProductsById__returns200() throws Exception {
        when(subcategoryService.getSubcategoriesIdWithProducts(1L)).thenReturn(SubcategoryData.SUBCATEGORIESWITHPRODUCTSDTO.get(0));

        // Simula una peticion GET a subcategoryWithProducts, buscando un ID específico
        mockMvc.perform(get(BASE_URL + "/subcategoryWithProducts/1"))
                .andExpect(status().isOk());
    }

    // Simula un usuario con rol ADMIN para poder acceder a endpoints protegidos por 
    // Debemos agreggar el WithMockUser para simular un usuario autenticado, debido a que estas peticiones, debido al SprintTest, requieren autenticación 
    @Test
    @WithMockUser(roles = "ADMIN")
    void createSubcategory__returns200() throws Exception {
        SubcategoryDTO dto = SubcategoryData.SUBCATEGORIESDTO.get(0);

        when(subcategoryService.save(any(SubcategoryDTO.class))).thenReturn(dto);

        // Simula un POST con contenido JSON y verifica que rsponda correctamente 
        mockMvc.perform(post(BASE_URL + "/admin/subcategory")
                        .contentType(MediaType.APPLICATION_JSON)  //Indicamos el contendio de la solicitud, ahora es un JSON. 
                        .content(objectMapper.writeValueAsString(dto))) //Convertimos el DTO a JSON y enviamos la solicitud.
                .andExpect(status().isOk()); 
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteSubcategory__returns200() throws Exception {
        SubcategoryDTO dto = SubcategoryData.SUBCATEGORIESDTO.get(0);

        // Simulamos la busqueda por el id
        when(subcategoryService.findById(1L)).thenReturn(dto);

        //Simulamos un Delete, que no hace nada realmente. 
        doNothing().when(subcategoryService).delete(dto);

        // Simulamos el Delte
        mockMvc.perform(delete(BASE_URL + "/admin/subcategory/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSubcategory__returns200() throws Exception {
        //Creamos Un DTO de la subcategoria para actalizarla. 
        SubcategoryDTO dto = SubcategoryData.SUBCATEGORIESDTO.get(0);
        Subcategory updated = SubcategoryData.SUBCATEGORIES.get(1);
        SubcategoryDTO updatedDto = SubcategoryData.SUBCATEGORIESDTO.get(1);

        //Simulamos encontrar la subcategoria y luego actualizarla
        when(subcategoryService.findById(1L)).thenReturn(dto);
        when(subcategoryService.update(eq(dto), any(Subcategory.class))).thenReturn(updatedDto);

        //Simula un PUT con el nuevo contenido
        mockMvc.perform(put(BASE_URL + "/admin/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    /*
    ------------------------ 
    Estas prubas buscan verificar que el controlador maneje correctamente los errores de validación y excepciones
    ------------------------
    */
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void createSubcategory__returns400_whenInvalidBody() throws Exception {
        // Enviamos un body vacío 
        mockMvc.perform(post(BASE_URL + "/admin/subcategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")) // Aca va a fallar debido a que no se envía un JSON válido y se espera que estos fallen 
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSubcategory__returns400_whenInvalidBody() throws Exception {

        // Se simula un PUT con contenido mal formado
        mockMvc.perform(put(BASE_URL + "/admin/subcategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid-json}"))// JSON inválido
                .andExpect(status().isBadRequest());
    }

    @Test
    void getSubcategoryWithProductsById__returns404_whenNotFound() throws Exception {

        // Simulamos que el servicio lanza una excepción o devuelve null 
        when(subcategoryService.getSubcategoriesIdWithProducts(999L)).thenReturn(null); 
        mockMvc.perform(get(BASE_URL + "/subcategoryWithProducts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteSubcategory__returns404__whenNotFound() throws Exception {
        when(subcategoryService.findById(999L)).thenReturn(null);

        mockMvc.perform(delete(BASE_URL + "/admin/subcategory/999"))
                .andExpect(status().isNotFound());
    }

}
