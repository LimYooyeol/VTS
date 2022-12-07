package com.stock.db.dto.Board;

import lombok.Getter;
import lombok.Setter;

/*
    @brief  : 게시물 페이징 시 사용
 */
@Getter @Setter
public class Criteria {

    private int page;
    private int size;

    // [0: 전체, 1: 국내 주식, 2: 기타]
    private int category;

    private String cno;

    private String title;

    private String writer;

    public Criteria(int category, int page, int size){
        this.category = category;
        this.page = page;
        this.size = size;
    }

    public Criteria(int category, int page, int size, String cno, String title, String writer) {
        this.category = category;
        this.page = page;
        this.size = size;
        this.cno = cno;
        this.title = title;
        this.writer = writer;
    }

    public int getSkip(){
        return (page - 1)*size;
    }
}
