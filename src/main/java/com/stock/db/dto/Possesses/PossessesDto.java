package com.stock.db.dto.Possesses;

import lombok.Data;

@Data
public class PossessesDto {
    private int mno;

    private String cno;

    private long quantity;

    private double avgPrice;
}
