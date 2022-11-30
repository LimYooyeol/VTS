package com.stock.db.dto.Orders;

import com.stock.db.domain.OrderState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MakeOrdersDto {
    private int ono;

    private int mno;

    private String cno;

    private Boolean isSale;

    private long quantity;

    private int price;
}
