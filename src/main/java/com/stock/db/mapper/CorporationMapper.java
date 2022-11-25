package com.stock.db.mapper;

import com.stock.db.domain.CorporationVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CorporationMapper {

    /*
        @brief  : 기업코드로 기업 조회
        @return : 조회한 기업
        @param  :
            cno : 기업코드
    */
    public CorporationVO findByCno(String cno);
}
