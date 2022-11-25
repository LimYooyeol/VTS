package com.stock.db.domain;


import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PriceVO {
    private String cno;
    private LocalDate date;

    private int open;
    private int close;
    private int high;
    private int low;

    private long volume;
    private double changeRate;
}
