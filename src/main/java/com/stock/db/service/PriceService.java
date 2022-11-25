package com.stock.db.service;

import com.stock.db.domain.PriceVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PriceService {

    /*
        @brief  : 기업코드로 주가 흐름 조회
        @return : 조회한 기업의 주가 목록( 조회일 기준 이전)
        @param  :
            cno : 기업코드
     */
    @Transactional(readOnly = true)
    public List<PriceVO> getPriceHistoryByCno(String cno){

        return null;
    }
}
