package com.stock.db.dto.Price;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistorySearchDto {
    private String cno;

    private int interval;
}
