package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/api/lineitem")
public class LineItemController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        LineItemDao lineItemDataStore = LineItemDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        Gson gson = new Gson();

        try {
            String[] ids = reader.readLine().split(",");
            int orderId = Integer.parseInt(ids[0]);
            int productId = Integer.parseInt(ids[1]);

            Product product = productDataStore.find(productId);
            Order order = orderDataStore.find(orderId);
            LineItem lineItem = order.findLineItemForProduct(product);
            if (lineItem != null){
                lineItem.increaseQuantity();
            } else {
                lineItem = new LineItem(product);
                lineItemDataStore.add(lineItem);
                order.addLineItem(lineItem);
            }
            String responseJSON = gson.toJson(lineItem);
            PrintWriter out = resp.getWriter();
            out.println(responseJSON);
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new ArrayIndexOutOfBoundsException("Not enough ID provided!");
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Invalid format for integer!");
        } catch (NullPointerException exception) {
            throw new NullPointerException("There's no order or product stored in the system with the given id!");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
