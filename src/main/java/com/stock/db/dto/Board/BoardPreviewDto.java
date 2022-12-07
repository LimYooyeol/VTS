package com.stock.db.dto.Board;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BoardPreviewDto {
    // 게시물 정보
    private int bno;

    private LocalDateTime writeDate;

    private String title;

    private String content;

    private int hit;
    private int fav;

    // 회원 정보
    private int mno;
    private String nickname;

    // 기업 정보
    private String cno;
    private String cname;
}
