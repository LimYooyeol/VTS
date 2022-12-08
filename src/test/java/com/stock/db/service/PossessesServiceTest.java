package com.stock.db.service;

import com.stock.db.dto.Possesses.PossessesDetailDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PossessesServiceTest {

    @Autowired PossessesService possessesService;

    @Test
    public void 보유주식_테스트(){
        // given

        // when
        List<PossessesDetailDto> possesses = possessesService.findPossessesByUserId("1");

        // then
        for(PossessesDetailDto p : possesses){
            System.out.println(p);
        }
    }
}