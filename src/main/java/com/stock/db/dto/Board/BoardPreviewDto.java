package com.stock.db.dto.Board;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BoardPreviewDto {
    private String cname;

    private int bno;

    private String title;

    private int reply;

    private LocalDate writeDate;
}
