package com.modscleo4.framework.web.controllers;

import app.web.Main;
import com.modscleo4.framework.collection.ICollection;
import com.modscleo4.framework.collection.IRow;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class Controller {
    protected void loadView(String view) throws ServletException, IOException {
        HttpServletRequest request = Main.request;
        HttpServletResponse response = Main.response;

        request.getRequestDispatcher(view).forward(request, response);
    }

    protected void loadJson(ICollection array) throws ServletException, IOException {
        HttpSession session = Main.request.getSession();

        session.setAttribute("array", array);
        this.loadView("/framework/json_array.jsp");
    }

    protected void loadJson(IRow object) throws ServletException, IOException {
        HttpSession session = Main.request.getSession();

        session.setAttribute("object", object);
        this.loadView("/framework/json_object.jsp");
    }

    protected void abort(int code, String message) throws IOException {
        HttpServletRequest request = Main.request;
        HttpServletResponse response = Main.response;

        response.sendError(code, message);
    }

    protected void abort(int code) throws IOException {
        abort(code, null);
    }
}
