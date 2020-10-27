package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.memory.OrderDaoMem;
import com.codecool.shop.dao.memory.UserDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.OrderData;
import com.codecool.shop.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/api/order")
public class OrderController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        UserDao userDataStore = UserDaoMem.getInstance();

        try {
            int userId = Integer.parseInt(reader.readLine());
            Order newOrder = new Order(userId);
            User user = userDataStore.find(userId);
            user.addOrder(newOrder);
            int orderId = orderDataStore.add(newOrder);
            PrintWriter out = resp.getWriter();
            out.println(orderId);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Invalid format for user id!");
        } catch (NullPointerException exception) {
            throw new NullPointerException("There's no user stored in the system with the given id!");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        OrderDao orderDataStore = OrderDaoMem.getInstance();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String response = stringBuilder.toString();

        try {
            OrderData orderData = gson.fromJson(response, OrderData.class);
            orderDataStore.find(orderData.getOrderId()).saveData(orderData);
            PrintWriter writer = resp.getWriter();
            writer.println(orderData.getOrderId());
        } catch (JsonSyntaxException exception) {
            throw new JsonSyntaxException("Request body has incorrect format");
        } catch (NullPointerException exception) {
            throw new NullPointerException("There's no order stored in the system with the given id!");
        }
    }
}
