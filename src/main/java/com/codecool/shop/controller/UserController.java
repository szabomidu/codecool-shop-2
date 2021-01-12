package com.codecool.shop.controller;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.database.UserDaoJdbc;
import com.codecool.shop.dao.memory.UserDaoMem;
import com.codecool.shop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/user")
public class UserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDataStore;
        try {
            userDataStore = UserDaoJdbc.getInstance();
            int id = userDataStore.add(new User("Guest"));
            PrintWriter out = resp.getWriter();
            out.println(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
