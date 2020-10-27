package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.memory.ProductDaoMem;
import com.codecool.shop.dao.memory.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.net.URISyntaxException;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            readSuppliers();
            readCategories();
            readProducts();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void readSuppliers() throws IOException, URISyntaxException {
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        File file = new File(Initializer.class.getClassLoader().getResource("suppliers.txt").toURI());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] attributes = line.split(",");
            String name = attributes[0];
            String description = attributes[1];
            supplierDataStore.add(new Supplier(name, description));
        }
    }

    private void readCategories() throws IOException, URISyntaxException {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        File file = new File(Initializer.class.getClassLoader().getResource("categories.txt").toURI());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] attributes = line.split(",");
            String name = attributes[0];
            String description = attributes[1];
            productCategoryDataStore.add(new ProductCategory(name, "Figure", description));
        }
    }

    private void readProducts() throws IOException, URISyntaxException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        File file = new File(Initializer.class.getClassLoader().getResource("products.txt").toURI());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] attributes = line.split(",");
            String name = attributes[0];
            float defaultPrice = Float.parseFloat(attributes[1]);
            String currency = attributes[2];
            String description = attributes[3];
            ProductCategory productCategory = productCategoryDataStore.findByName(attributes[4]);
            Supplier supplier = supplierDataStore.findByName(attributes[5]);

            productDataStore.add(new Product(name, defaultPrice, currency, description, productCategory, supplier));
        }
    }
}
