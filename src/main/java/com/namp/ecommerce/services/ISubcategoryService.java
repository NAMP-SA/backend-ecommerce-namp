package com.namp.ecommerce.services;



import com.namp.ecommerce.models.Subcategory;

import java.util.List;

public interface ISubcategoryService {
    List<Subcategory> getSubcategories();
    Subcategory save(Subcategory subcategory);
    Subcategory update(Subcategory existingSubcategory, Subcategory subcategory);
    void delete(Subcategory subcategory);
    Subcategory findById(long id);
    boolean verifyName(String normalizedName);
    boolean verifyName(String normalizedName, long idSubcategory);
}
