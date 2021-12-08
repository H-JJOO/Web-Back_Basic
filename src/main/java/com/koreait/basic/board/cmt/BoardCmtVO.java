package com.koreait.basic.board.cmt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardCmtVO {
    private int icmt;
    private String ctnt;
    private int writer;
    private String rdt;
    private String writerNm;
}
