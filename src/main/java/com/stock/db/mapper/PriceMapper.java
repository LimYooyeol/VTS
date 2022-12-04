package com.stock.db.mapper;

import com.stock.db.domain.PriceVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PriceMapper {

    /*
        @brief  : 기업코드로 주가 흐름 조회
        @return : 조회한 기업의 주가 목록( 조회일 기준 이전)
        @param  :
            cno : 기업코드
     */
    public List<PriceVO> getPriceHistoryByCno(String cno);

    /*
        @brief  : 개장 여부 검사
        @return : [0: close, 1: open]
     */

    /*
        @brief : 개장 여부 검사
        @return
            true : 개장
            false: 휴장
     */
    public boolean isMarketOpen();

    /*
        @brief  : 그날의 시가와 고가 사이 인지 확인
        @param  :
            cno     : 기업코드
            price   : 확인할 가격
        @return :
            true    : 시가와 고가 사이의 가격
            false   : 시가와 고가 사이의 가격이 아님
     */
    public boolean isPriceBetweenLowAndHigh(String cno, int price);

    /*
        @brief  : 종목의 시가 확인
        @param  :
            cno : 기업코드
        @return : 시가
     */
    public int getOpen(String cno);
}

