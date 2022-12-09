package com.stock.db.mapper;

import com.stock.db.domain.CorporationVO;
import com.stock.db.dto.Corporation.CorporationBriefDto;
import com.stock.db.dto.Corporation.CorporationCriteria;
import com.stock.db.dto.Corporation.SectorChangeRateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;

@Mapper
public interface CorporationMapper {

    /*
        @brief  : 기업코드로 기업 조회
        @return : 조회한 기업
        @param  :
            cno : 기업코드
    */
    public CorporationVO findByCno(String cno);

    /*
        @brief  : 상승률 상위 종목 조회
        @return : 상승률 상위 종목 목록
        @param  :
            num : 조회할 종목의 수(상위 몇 개 종목까지 조회할 것인지)
     */
    public List<CorporationVO> getTopRisingCorporations(int num);


    /*
        @brief  : 상승률 상위 섹터 조회
        @return : 상승률 상위 섹터의 이름 목록
        @param  :
            num : 조회할 섹터의 수(상위 몇 개 섹터까지 조회할 것인지)
     */
    public List<SectorChangeRateDto> getTopRisingSectors(int num);


    /*
        @brief  : 검색 조건에 해당하는 종목 목록 조회
        @return : 검색 조건에 해당 하는 종목 목록
        @param  :
            corporationCriteria : 종목 검색 조건(페이징 정보, 정렬 조건, 등락률 필터, 종목명)
     */
    public List<CorporationVO> getPage(CorporationCriteria corporationCriteria);

    /*
        @brief  : 해당 검색 조건에서 최대 페이지 수 설정
        @return : 해당 검색 조건에서 최대 페이지 수
        @param  :
            corporationCriteria : 종목 검색 조건(페이징 정보, 정렬 조건, 등락률 필터, 종목명)

     */
    public int getMaxPageNum(CorporationCriteria criteria);

    /*
        @brief  : 상승률 하위 종목 조회
        @return : 상승률 하위 종목 목록
        @param  :
            num : 조회할 종목의 수(상위 몇 개 종목까지 조회할 것인지)
     */
    public List<CorporationVO> getTopDropCorporations(int num);


    /*
        @brief  : 거래량 상위 종목 조회
        @return : 거래량 상위 종목 목록
     */
    public List<CorporationVO> getTopTradingCorporations(int num);

    public List<CorporationBriefDto> getCnames();
}
