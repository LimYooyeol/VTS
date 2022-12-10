package com.stock.db.dto.Orders;

import lombok.Data;

@Data
public class OrdersDetailDto {
    private String cno;

    private String cname;

    private boolean isSale;

    private int price;

    private long quantity;

    private int ono;

    private String state;
}
