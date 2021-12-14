package com.koreait.basic.user;

import com.koreait.basic.Utils;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String title = "프로필";
        req.setAttribute("subPage", "/user/profile");
        Utils.displayView(title, "/user/myPage", req, res);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int loginUserPk = Utils.getLoginUserPk(req);//폴더값 주기위해서 가져온 Pk값
        int maxSize = 10_485_760; //1024 * 1024 * 10 (10mb), 이미지 최대사이즈

        ServletContext application = req.getServletContext();
        String targetPath = application.getRealPath("/res/img/profile" + loginUserPk);
        File file = new File(targetPath);
        file.mkdir();
        System.out.println("targetPath : " + targetPath);

        MultipartRequest mr = new MultipartRequest(req, targetPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
        //new DefaultFileRenamePolicy() -> 중복이 안되게 이름 바꿔주는 역할(_)
        Enumeration files = mr.getFileNames();

        String fileNm = (String) files.nextElement();
        System.out.println("fileNm : " + fileNm);
    }
}
