package com.koreait.basic;

import com.koreait.basic.user.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    public static int parseStringToInt(String str, int defVal) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {}
            return defVal;
    }

    public static int parseStringToInt(String str) {
        return parseStringToInt(str, 0);
    }

    public static int getParameterInt(HttpServletRequest req, String key) {
        String strVal = req.getParameter(key);
        int intVal = parseStringToInt(strVal);
        return intVal;
    }

    public static UserEntity getLoginUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return  (UserEntity)session.getAttribute("loginUser");
    }

    public static boolean isLogout(HttpServletRequest req) {
        return getLoginUser(req) == null;
    }

    public static boolean isLogin(HttpServletRequest req) {
        return getLoginUser(req) != null;
    }

    //예외발생을 막기위해서 null 이면 0 return 하도록
    public static int getLoginUserPk(HttpServletRequest req) {
        UserEntity loginUser = getLoginUser(req);
        return  loginUser == null ? 0 : loginUser.getIuser();
    }


}
