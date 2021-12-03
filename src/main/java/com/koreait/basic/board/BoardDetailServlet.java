package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req, "iboard");
        //TODO writer 주면 된다고? 일단 대기

        BoardDTO param = new BoardDTO();
        param.setIboard(iboard);

        int prevIboard = BoardDAO.selPrevIboard(param);
        int nextIboard = BoardDAO.selNextIboard(param);

        BoardVO data = BoardDAO.selBoardDetail(param);

        //로그인 한 사람이 pk값과 data 에 들어있는 writer 값이 다르거나
        //로그인이 안 되어 있으면  hit값을 올려
        int loginUserPk = Utils.getLoginUserPk(req);
        if (data.getWriter() != loginUserPk) {
            BoardDAO.updBoardHitUp(param);
        }

        req.setAttribute("data", data);
        req.setAttribute("prevIboard", prevIboard);
        req.setAttribute("nextIboard", nextIboard);

        Utils.displayView(data.getTitle(), "/board/detail", req, res);
    }
}
