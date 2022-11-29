package com.stock.db.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
public class OrdersVO {
    private int ono;

    private int mno;

    private String cno;

    private LocalDateTime orderTime;

    private LocalDateTime tradeTime;

    private boolean isSale;

    private OrderState state;

    private long quantity;

    private double gain;

    private double price;

}
