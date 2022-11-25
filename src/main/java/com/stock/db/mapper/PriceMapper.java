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
}

