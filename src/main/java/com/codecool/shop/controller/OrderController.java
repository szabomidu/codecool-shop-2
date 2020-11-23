package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.database.ConnectionHandler;
import com.codecool.shop.dao.database.LineItemDaoJdbc;
import com.codecool.shop.dao.database.OrderDaoJdbc;
import com.codecool.shop.dao.database.UserDaoJdbc;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.OrderData;
import com.codecool.shop.model.User;
import com.codecool.shop.utility.MailHandler;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/order")
public class OrderController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        OrderDao orderDataStore;
        try {
			DataSource dataSource = ConnectionHandler.getDataSource();
			orderDataStore = OrderDaoJdbc.getInstance();
            UserDao userDataStore = new UserDaoJdbc(dataSource);
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        OrderDao orderDataStore;
        LineItemDao lineItemDataStore;
        try {
            orderDataStore = OrderDaoJdbc.getInstance();
            lineItemDataStore = LineItemDaoJdbc.getInstance();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String response = stringBuilder.toString();
            OrderData orderData = gson.fromJson(response, OrderData.class);
            Order order = orderDataStore.find(orderData.getOrderId());
            order.saveData(orderData);
            orderDataStore.update(order);

            MailHandler mailHandler = new MailHandler(order, lineItemDataStore.getBy(order));
            mailHandler.sendMail();

            PrintWriter writer = resp.getWriter();
            writer.println(orderData.getOrderId());
        } catch (JsonSyntaxException exception) {
            throw new JsonSyntaxException("Request body has incorrect format");
        } catch (NullPointerException exception) {
            throw new NullPointerException("There's no order stored in the system with the given id!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
