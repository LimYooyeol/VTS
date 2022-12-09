package com.stock.db.dto.Orders;

import com.stock.db.domain.OrderState;
import com.stock.db.dto.Board.BoardPreviewDto;
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

    public MakeOrdersDto(int mno, String cno, Boolean isSale, long quantity, int price){
        this.mno = mno;
        this.cno = cno;
        this.isSale = isSale;
        this.quantity = quantity;
        this.price = price;
    }
}
