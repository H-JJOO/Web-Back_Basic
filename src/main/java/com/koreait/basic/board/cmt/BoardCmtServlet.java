package com.koreait.basic.board.cmt;

import com.google.gson.Gson;
import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardDTO;
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
        //리스트 (R)
        int iboard = Utils.getParameterInt(req, "iboard");
        BoardCmtDTO cmtParam = new BoardCmtDTO();
        cmtParam.setIboard(iboard);

        List<BoardCmtVO> cmtlist = BoardCmtDAO.selBoardCmtList(cmtParam);

        Gson gson = new Gson();// Object -> Json 으로 바꾸기 위해 Gson 객체 생성
        String json = gson.toJson(cmtlist);

        res.setContentType("application/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();//응답하는 역할
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //등록(C), 수정(U), 삭제(D)
        String proc = req.getParameter("proc");

        String json = Utils.getJson(req);
        Gson gson = new Gson();

        BoardCmtEntity entity = gson.fromJson(json, BoardCmtEntity.class);
        entity.setWriter(Utils.getLoginUserPk(req));//등록, 수정, 삭제때 다필요함

        int result = 0;//결과값을 주기위한 셋팅
        switch (proc) {
            case "upd" :
                result = BoardCmtDAO.updBoardCmt(entity);//writer, icmt, ctnt
                break;
        }

        res.setContentType("application/json");
        PrintWriter out = res.getWriter();

        //Map
        Map<String, Integer> map = new HashMap();//여러개를 저장할수 있는데 순서개념 없음, Key 값과 Value 값이이 중요
        map.put("result", result);//키값, 벨류값

        String resultJson = gson.toJson(map);
        System.out.println("resultJson : " + resultJson);
        out.println(resultJson);
//        out.print(String.format("{\"result\": %d}", result));


    }
}
