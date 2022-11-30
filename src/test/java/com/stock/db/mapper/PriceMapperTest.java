package com.stock.db.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PriceMapperTest {

    @Autowired PriceMapper priceMapper;

    /*
        @warning    : 날 마다 달라짐
     */
    @Test
    public void 가격검사_테스트(){
        // given
        String cno = "005930";
        int price = 61500;

        // when
        boolean result = priceMapper.isPriceBetweenLowAndHigh(cno, price);

        // then
        assertFalse(result);

    }

}