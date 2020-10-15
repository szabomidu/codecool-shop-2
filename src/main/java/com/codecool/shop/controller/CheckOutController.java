package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/checkout")
public class CheckOutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        OrderDao orderData = OrderDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        int orderId = Integer.parseInt(req.getParameter("id"));
        Order order = orderData.find(orderId);
        System.out.println(order);
        List<LineItem> lineItems = order.getLineItems();

        double totalPrice = 0;
        int itemsInCart = 0;
        for (LineItem lineItem : lineItems) {
            totalPrice += lineItem.getTotalPrice();
            itemsInCart += lineItem.getQuantity();
        }

        context.setVariable("lineItems", lineItems);
        context.setVariable("totalPrice", Math.round(totalPrice * 100.0) / 100.0);
        context.setVariable("itemsInCart", itemsInCart);

        engine.process("checkout/checkout.html", context, resp.getWriter());

    }
}
