package com.stock.db.mapper;

import com.stock.db.domain.HoldersVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HoldersMapper {

    /*
        @brief  : 종목코드로 주요 주주 현황 조회
        @return : 주요 주주 목록
        @param  :
            cno : 종목 코드
     */
    public List<HoldersVO> getHolderListByCno(String cno);

}
