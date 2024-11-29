package com.namp.ecommerce.service;

import java.io.IOException;
import java.util.List;

import com.namp.ecommerce.dto.ComboDTO;
import com.namp.ecommerce.dto.ComboWithItDTO;


import org.springframework.web.multipart.MultipartFile;

public interface IComboService {
    List<ComboDTO> getCombos();
    List<ComboWithItDTO> getCombosWithIt();
    ComboDTO saveCombo(String product, MultipartFile file) throws IOException;
    ComboDTO update(ComboDTO comboDTO, String product, MultipartFile file) throws IOException;
    void delete(ComboDTO comboDTO);
    ComboDTO findById(Long id);
    ComboWithItDTO findByIdWithIt(Long id);
    boolean verifyName(String normalizedName);
    boolean verifyName(String normalizedName, long idProduct);
    void decreaseStock(ComboDTO comboDTO,int quantity);
    boolean checkStock(ComboDTO comboDTO, int quantity);
}
