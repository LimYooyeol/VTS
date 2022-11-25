package com.stock.db.service;

import com.stock.db.domain.HoldersVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HoldersServiceTest {

    @Autowired HoldersService holdersService;

    /*
        @warning    : 주주 목록이 DB에 있는 경우에만 유효
     */
    @Test
    @DisplayName("주주조회 성공")
    public void 주주조회_테스트(){
        // given
        String cno = "005930";

        // when
        List<HoldersVO> findHolders = holdersService.getHolderListByCno(cno);

        // then
        assertNotNull(findHolders);
        assertEquals(3, findHolders.size());
    }

}