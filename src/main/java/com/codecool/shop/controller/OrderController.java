package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;

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
            Order newOrder = new Order();
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
}
