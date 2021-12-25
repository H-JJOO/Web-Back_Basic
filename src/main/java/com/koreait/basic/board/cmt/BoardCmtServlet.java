package com.koreait.basic.board.cmt;

import com.google.gson.Gson;
import com.koreait.basic.Utils;
import com.koreait.basic.dao.BoardCmtDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/board/cmt")
public class BoardCmtServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //댓글리스트, iboard 값을 받아와서 댓글 리스트 뿌릴거임
        int iboard = Utils.getParameterInt(req, "iboard");
        BoardCmtDTO cmtParam = new BoardCmtDTO();
        cmtParam.setIboard(iboard);

        List<BoardCmtVO> cmtList = BoardCmtDAO.selBoardCmtList(cmtParam); //댓글 리스트를

        Gson gson = new Gson();//json 구조의 데이터를 JAVA 객체로 바꿔주는 JAVA 라이브러리 (json <-> java 를 돕는 라이브러리)
        String json = gson.toJson(cmtList);//cmtList 를 Json 으로 바꿔줌

        res.setContentType("application/json;charset=UTF-8");//MIME 타입인 "text/html" 의 변경, 캐릭터 인코딩 재 지정
        res.setCharacterEncoding("UTF-8");//post 방식으로 날릴때 한글 안깨지게
        PrintWriter out = res.getWriter();//json 형태 텍스트를 출력해주기위한 출력 메서드
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //CUD
        String proc = req.getParameter("proc");

        String json = Utils.getJson(req);
        Gson gson = new Gson();

        BoardCmtEntity entity = gson.fromJson(json, BoardCmtEntity.class);
        entity.setWriter(Utils.getLoginUserPk(req));//writer 값 cud 다필요함

        int result = 0;
        switch (proc) {
            case "upd" :
                result = BoardCmtDAO.updBoardCmt(entity);//writer, icmt, ctnt
                //댓글수정할때는 작성자와 댓글번호, 댓글내용이 있어야 수정가능
                break;
            case "del" :
                result = BoardCmtDAO.delBoardCmt(entity);//writer, icmt
                //댓글삭제할때는 작성자와 댓글번호가 있어야 삭제가능
                break;
            case "ins" :
                result = BoardCmtDAO.insBoardCmt(entity);//writer, iboard, ctnt
                //댓글등록할때는 작성자와 게시글번호, 댓글내용이 있어야 등록가능
                break;
        }
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();

        Map<String, Integer> map = new HashMap();
        map.put("result", result);

        String resultJson = gson.toJson(map);
        out.println(resultJson);
    }
}
