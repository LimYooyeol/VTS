package com.stock.db.domain;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class PossessesVO {
    private int mno;

    private String cno;

    private long quantity;

    private double avgPrice;
}
