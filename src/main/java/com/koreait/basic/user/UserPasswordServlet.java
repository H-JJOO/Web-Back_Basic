package com.koreait.basic.user;

import com.koreait.basic.Utils;
import com.koreait.basic.dao.UserDAO;
import com.koreait.basic.user.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/password")
public class UserPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String title = "비밀번호 변경";
        req.setAttribute("subPage", "/user/password");//열어야할 jsp 파일명
        Utils.displayView(title, "/user/myPage", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String upw = req.getParameter("upw");
        String changedUpw = req.getParameter("changedUpw");
        int loginUserPk = Utils.getLoginUserPk(req);

        UserEntity entity = new UserEntity();
        entity.setIuser(loginUserPk);

        UserEntity myInfo = UserDAO.selUser(entity);
        if (BCrypt.checkpw(upw, myInfo.getUpw())) {//현재 비밀번호가 맞다면
            String hashUpw = BCrypt.hashpw(changedUpw, BCrypt.gensalt());
            entity.setUpw(hashUpw);
            UserDAO.updUser(entity);
            res.sendRedirect("/user/logout");
            return;
        }
        //현재 비밀번호가 다르다면
        req.setAttribute("err", "현재 비밀번호를 확인해 주세요.");
        doGet(req, res);
    }
}
