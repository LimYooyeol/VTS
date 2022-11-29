package com.stock.db.service;

import com.stock.db.domain.CorporationVO;
import com.stock.db.dto.Corporation.SectorChangeRateDto;
import com.stock.db.mapper.CorporationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CorporationService {

    private final CorporationMapper corporationMapper;

    /*
        @brief  : 기업코드로 기업 조회
        @return : 조회한 기업
        @param  :
            cno : 기업코드
     */
    @Transactional(readOnly = true)
    public CorporationVO findByCno(String cno){

        return null;
    }


    /*
        @brief  : 상승률 상위 종목 조회
        @return : 상승률 상위 종목 목록
        @param  :
            num : 조회할 종목의 수(상위 몇 개 종목까지 조회할 것인지)
     */
    @Transactional(readOnly = true)
    public List<CorporationVO> getTopRisingCorporations(int num){
        List<CorporationVO> findCorps = corporationMapper.getTopRisingCorporations(num);

        /*
            #휴장인 경우 처리하는 로직 추가하기
         */

        return findCorps;
    }


    /*
        @brief  : 상승률 상위 섹터 조회
        @return : 상승률 상위 섹터의 이름 목록
        @param  :
            num : 조회할 섹터의 수(상위 몇 개 섹터까지 조회할 것인지)
     */
    @Transactional(readOnly = true)
    public List<SectorChangeRateDto> getTopRisingSectors(int num){
        List<SectorChangeRateDto> findSectors = corporationMapper.getTopRisingSectors(num);

        /*
            #휴장인 경우 처리하는 로직 추가하기
         */

        return findSectors;
    }
}
