package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.database.LineItemDaoJdbc;
import com.codecool.shop.dao.database.OrderDaoJdbc;
import com.codecool.shop.dao.database.ProductDaoJdbc;
import com.codecool.shop.dao.memory.LineItemDaoMem;
import com.codecool.shop.dao.memory.OrderDaoMem;
import com.codecool.shop.dao.memory.ProductDaoMem;
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
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/lineitem")
public class LineItemController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        LineItemDao lineItemDataStore;
        try {
            lineItemDataStore = LineItemDaoJdbc.getInstance();
            ProductDao productDataStore = ProductDaoJdbc.getInstance();
            OrderDao orderDataStore = OrderDaoJdbc.getInstance();
            Gson gson = new Gson();

            String line = reader.readLine();
            String[] ids = line.substring(1, line.length() - 1).split(",");
            int orderId = Integer.parseInt(ids[0]);
            int productId = Integer.parseInt(ids[1]);

            Product product = productDataStore.find(productId);
            Order order = orderDataStore.find(orderId);
            LineItem lineItem = lineItemDataStore.getBy(order, product);
            if (lineItem != null) {
                lineItemDataStore.update(lineItem, 1);
            } else {
                lineItem = new LineItem(product, order.getId());
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        LineItemDao lineItemDataStore;
        try {
            lineItemDataStore = LineItemDaoJdbc.getInstance();
            Gson gson = new Gson();
            String line = reader.readLine();
            String[] numbers = line.substring(1, line.length() - 1).split(",");
            int lineItemId = Integer.parseInt(numbers[0]);
            int quantityChange = Integer.parseInt(numbers[1]);

            LineItem lineItem = lineItemDataStore.find(lineItemId);
            lineItemDataStore.update(lineItem, quantityChange);

            PrintWriter out = resp.getWriter();
            String responseJSON = gson.toJson(lineItem);
            out.println(responseJSON);
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new ArrayIndexOutOfBoundsException("Not enough ID provided!");
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Invalid format for integer!");
        } catch (NullPointerException exception) {
            throw new NullPointerException("There's no line item stored in the system with the given id!");
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Quantity cannot decrease below zero");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        LineItemDao lineItemDataStore;
        try {
            lineItemDataStore = LineItemDaoJdbc.getInstance();
            OrderDao orderDataStore = OrderDaoJdbc.getInstance();
            String line = reader.readLine();
            String[] numbers = line.substring(1, line.length() - 1).split(",");
            int orderId = Integer.parseInt(numbers[0]);
            int lineItemId = Integer.parseInt(numbers[1]);

            LineItem lineItem = lineItemDataStore.find(lineItemId);
            Order order = orderDataStore.find(orderId);
            int quantityChange = -lineItem.getQuantity();
            order.removeLineItem(lineItem);
            lineItemDataStore.remove(lineItemId);

            PrintWriter out = resp.getWriter();
            out.println(quantityChange);
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new ArrayIndexOutOfBoundsException("Not enough ID provided!");
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Invalid format for integer!");
        } catch (NullPointerException exception) {
            throw new NullPointerException("There's no line item or order stored in the system with the given id!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
