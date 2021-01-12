package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.database.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.database.ProductDaoJdbc;
import com.codecool.shop.dao.database.SupplierDaoJdbc;
import com.codecool.shop.dao.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.memory.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.memory.SupplierDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore;
        try {
            productDataStore = ProductDaoJdbc.getInstance();
            ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
            SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("categories", productCategoryDataStore.getAll());
            context.setVariable("suppliers", supplierDataStore.getAll());
            context.setVariable("products", productDataStore.getAll());
            engine.process("product/index.html", context, resp.getWriter());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
