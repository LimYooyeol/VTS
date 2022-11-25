package com.stock.db.service;

import com.stock.db.domain.CorporationVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CorporationServiceTest {

    @Autowired CorporationService corporationService;

    /*
        @warning : DB에 기업 정보가 들어가 있는 경우에만 유효
     */
    @Test
    @DisplayName("기업조회 성공")
    public void 기업조회_테스트(){
        //given
        String cno = "005930";  //삼성전자

        //when
        CorporationVO findCorp = corporationService.findByCno(cno);    //삼성전자

        //then
        assertNotNull(findCorp);
        assertEquals("삼성전자", findCorp.getCname());
    }
}