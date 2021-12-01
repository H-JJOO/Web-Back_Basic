package com.koreait.basic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Utils {
    public static void displayView(String title, String view, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("title", title);
        req.setAttribute("page", view);
        disForward(req, res, "/layout");
    }

    public static void disForward(HttpServletRequest req, HttpServletResponse res, String jsp) throws ServletException, IOException {
        String path = "/WEB-INF/view" + jsp + ".jsp";
        req.getRequestDispatcher(path).forward(req, res);
    }
}
