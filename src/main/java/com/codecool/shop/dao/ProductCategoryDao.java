package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;

public interface ProductCategoryDao {

    void add(ProductCategory category);
    void addAll(ProductCategory... categories);
    ProductCategory find(int id);
    ProductCategory findByName(String name);
    void remove(int id);

    List<ProductCategory> getAll();

}
