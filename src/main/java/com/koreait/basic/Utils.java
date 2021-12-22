package com.koreait.basic;

import com.koreait.basic.user.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import java.io.*;

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

    public static int getParameterInt(HttpServletRequest req, String key, int defVal) {
        return parseStringToInt(req.getParameter(key), defVal);
    }

    public static UserEntity getLoginUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return  (UserEntity)session.getAttribute("loginUser");
    }

    //예외발생을 막기위해서 null 이면 0 return 하도록
    public static int getLoginUserPk(HttpServletRequest req) {
        UserEntity loginUser = getLoginUser(req);
        return  loginUser == null ? 0 : loginUser.getIuser();
    }

    public static String getJson(HttpServletRequest request) throws IOException {
        String reqStr = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        reqStr = stringBuilder.toString();
        return reqStr;
    }
}

