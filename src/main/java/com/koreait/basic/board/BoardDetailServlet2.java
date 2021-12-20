package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.cmt.BoardCmtDTO;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardHeartEntity;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardCmtDAO;
import com.koreait.basic.dao.BoardDAO;
import com.koreait.basic.dao.BoardHeartDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/detail2")
public class BoardDetailServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int nohits = Utils.getParameterInt(req, "nohits");//야매로 댓글달때 조회수 안오르게
        int iboard = Utils.getParameterInt(req, "iboard");

        BoardDTO param = new BoardDTO();
        param.setIboard(iboard);

        int prevIboard = BoardDAO.selPrevIboard(param);
        int nextIboard = BoardDAO.selNextIboard(param);

        BoardVO data = BoardDAO.selBoardDetail(param);

        req.setAttribute("data", data);
        req.setAttribute("prevIboard", prevIboard);
        req.setAttribute("nextIboard", nextIboard);

        //로그인 한 사람이 pk값과 data 에 들어있는 writer 값이 다르거나
        //로그인이 안 되어 있으면  hit값을 올려
        int loginUserPk = Utils.getLoginUserPk(req);
        if (loginUserPk > 0) {//로그인이 되어 있어야 되고
            BoardHeartEntity bhEntity = new BoardHeartEntity();
            bhEntity.setIuser(loginUserPk);
            bhEntity.setIboard(iboard);
            req.setAttribute("isHeart", BoardHeartDAO.selIsHeart(bhEntity));
        }
        
        if (data.getWriter() != loginUserPk && nohits != 1) {//야매로 댓글달때 조회수 안오르게
            BoardDAO.updBoardHitUp(param);
        }
        Utils.displayView(data.getTitle(), "/board/detail2", req, res);
    }
}
