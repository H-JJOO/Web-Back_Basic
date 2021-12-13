package com.koreait.basic.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardHeartEntity {
    private int iuser;
    private int iboard;
    private String rdt;
    //BoardHeart는 DTO 랑 Entity를 같이 쓰도록한다, 필요시 VO만들어서 추가
}
