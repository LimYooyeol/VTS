package com.stock.db.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyVO {
    private int rno;

    private String content;

    private LocalDateTime writeDate;

    private int mno;

    private int bno;
}
