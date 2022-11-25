package com.stock.db.domain;

import lombok.Getter;

@Getter
public class CorporationVO {

    private String cno;

    private String cname;

    private String overview;

    private long sharesOutstanding;

    private String sector;
}
