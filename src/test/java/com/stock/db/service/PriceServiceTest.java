package com.stock.db.service;

import com.stock.db.domain.PriceVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PriceServiceTest {

    @Autowired PriceService priceService;

    @Test
    public void 주가흐름_조회_테스트(){
        // given
        String cno = "005930";  //삼성전자

        // when
        List<PriceVO> priceList = priceService.getPriceHistoryByCno(cno);

        // then
        assertNotNull(priceList);
    }
}