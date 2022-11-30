package com.stock.db.mapper;

import com.stock.db.domain.PossessesVO;
import com.stock.db.dto.Possesses.PossessesDto;
import com.stock.db.dto.Possesses.PossessesKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PossessesMapperTest {

    @Autowired PossessesMapper possessesMapper;

    /*
        @warning: 회원 번호 1 이 DB에 있는 경우에만 유효
     */
    @Test
    @DisplayName("보유정보 업데이트 및 조회 성공")
    public void 보유정보_업데이트_테스트(){
        // given
        int mno = 1;
        String cno = "005930";
        PossessesDto possessesDto = new PossessesDto();
        possessesDto.setMno(mno);
        possessesDto.setCno(cno);
        possessesDto.setQuantity(10);
        possessesDto.setAvgPrice(10000);

        // when
        possessesMapper.reflectPossesses(possessesDto);
        PossessesVO findPossesses = possessesMapper.findPossesses(mno, cno);

        // then
        assertNotNull(findPossesses);

        possessesDto.setQuantity(20);
        possessesDto.setAvgPrice(10500);
        possessesMapper.reflectPossesses(possessesDto);
        PossessesVO findAgain = possessesMapper.findPossesses(mno, cno);
        assertEquals(20, findAgain.getQuantity());
    }

}