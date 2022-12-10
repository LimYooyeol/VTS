package com.stock.db.dto.Board;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
    @brief  : 게시물 작성 시 필요한 데이터
 */
@Getter @Setter @ToString
public class BoardWriteDto {

    private int bno;

    private String title;

    private String content;

    private int mno;
    private String cno;
}
