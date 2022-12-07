package com.stock.db.dto.Corporation;

import com.stock.db.controller.CorporationController;

public class CorporationCriteria {

    private int page;

    private int size;

    // [0: 종목코드 오름차순, 1: 종목코드 내림차순, 2: 등락률 오름차순, 3: 등락률 내림차순]
    private int sorting;

    private Integer min;

    private Integer max;

    private String cname;

    public CorporationCriteria(int page, int size){
        this.page = page;
        this.size = size;
        this.sorting = 0;
    }

    public CorporationCriteria(int page, int size, int sorting, String cname, Integer min, Integer max){
        this.page = page;
        this.size = size;
        this.sorting = sorting;
        this.cname = cname;
        this.min = min;
        this.max = max;
    }

    public int getSkip(){
        return (this.page-1)*this.size;
    }

}
