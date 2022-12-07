package com.stock.db.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
public class BoardVO {
    private int bno;
    private String title;
    private String content;
    private LocalDateTime writeDate;
    private long hit;
    private long fav;

    // FKs
    private int mno;
    private String cno;
}
