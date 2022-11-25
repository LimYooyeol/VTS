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

    public Criteria(int page, int size){
        this.page = page;
        this.size = size;
    }
}
