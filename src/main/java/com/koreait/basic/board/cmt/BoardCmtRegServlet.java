package com.koreait.basic.board.cmt;

import com.koreait.basic.Utils;
import com.koreait.basic.dao.BoardCmtDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/cmt/reg")
public class BoardCmtRegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req, "iboard", 0);
        String ctnt = req.getParameter("ctnt");

        BoardCmtEntity param = new BoardCmtEntity();
        param.setIboard(iboard);
        param.setCtnt(ctnt);
        param.setWriter(Utils.getLoginUserPk(req));

        int result = BoardCmtDAO.insBoardCmt(param);

        switch (result) {
            case 1:
                res.sendRedirect("/board/detail?nohits=1&iboard=" + iboard);
                break;
            default:
                req.setAttribute("err", "댓글 등록을 실패하였습니다.");
                res.sendRedirect("/board/detail?nohits=1&iboard=" + iboard);
                break;
        }
    }
}
