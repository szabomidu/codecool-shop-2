package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;

public interface SupplierDao {

    void add(Supplier supplier);
    void addAll(Supplier ... suppliers);
    Supplier find(int id);
    Supplier findByName(String name);
    void remove(int id);

    List<Supplier> getAll();
}
