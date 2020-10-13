package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/api/filter")
public class FilterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        Gson gson = new Gson();

        int supplierId = Integer.parseInt(req.getParameter("supplier"));
        int categoryId = Integer.parseInt(req.getParameter("category"));
        List<Product> products;

        if (supplierId == 0) {
            if (categoryId == 0) {
                products = productDataStore.getAll();
            } else {
                products = productDataStore.getBy(productCategoryDataStore.find(categoryId));
            }
        } else if(categoryId == 0){
            products = productDataStore.getBy(supplierDataStore.find(supplierId));
        } else {
            products = productDataStore.getBy(supplierDataStore.find(supplierId), productCategoryDataStore.find(categoryId));
        }

        String responseJSON = gson.toJson(products);
        PrintWriter out = resp.getWriter();
        out.println(responseJSON);
    }

}