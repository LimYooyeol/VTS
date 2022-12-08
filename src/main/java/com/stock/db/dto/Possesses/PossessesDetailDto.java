package com.stock.db.dto.Possesses;

import lombok.Data;

@Data
public class PossessesDetailDto {
    private String cno;

    private String cname;

    private long quantity;

    private long totalPrice;

    private double gain;

    private double gainRatio;

    private long currentPrice;

    private double avgPrice;
}
