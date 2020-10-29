package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.database.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.database.ProductDaoJdbc;
import com.codecool.shop.dao.database.SupplierDaoJdbc;
import com.codecool.shop.dao.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.memory.ProductDaoMem;
import com.codecool.shop.dao.memory.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/api/filter")
public class FilterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = null;
        try {
            productDataStore = ProductDaoJdbc.getInstance();
            ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
            SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();
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
            } else if (categoryId == 0) {
                products = productDataStore.getBy(supplierDataStore.find(supplierId));
            } else {
                products = productDataStore.getBy(supplierDataStore.find(supplierId), productCategoryDataStore.find(categoryId));
            }

            String responseJSON = gson.toJson(products);
            PrintWriter out = resp.getWriter();
            out.println(responseJSON);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}