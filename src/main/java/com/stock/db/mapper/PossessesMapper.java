package com.stock.db.mapper;

import com.stock.db.domain.PossessesVO;
import com.stock.db.dto.Orders.MakeOrdersDto;
import com.stock.db.dto.Possesses.PossessesDetailDto;
import com.stock.db.dto.Possesses.PossessesDto;
import com.stock.db.dto.Possesses.PossessesKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PossessesMapper {

    /*
        @brief  : 사용자가 보유 중인 주식 조회
        @return : 사용자가 보유 중인 주식 목록
        @param  :
            userId : 사용자 ID
     */
    public List<PossessesDetailDto> findPossessesByUserId(String userId);

    /*
        @brief  : 체결 시 주식 보유 정보 반영
        @return : 업데이트 한 행의 개수
        @param  :
            possessesDto : 주식 보유 정보
     */
    public int reflectPossesses(PossessesDto possessesDto);

    /*
        @brief  : 보유 정보 조회
        @return : 조회한 정보
        @param  :
            possessesKey : 보유 정보 키(멤버 번호, 종목코드)
     */
    public PossessesVO findPossesses(int mno, String cno);
}
