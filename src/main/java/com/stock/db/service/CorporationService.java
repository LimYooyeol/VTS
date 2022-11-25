package com.stock.db.service;

import com.stock.db.domain.CorporationVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CorporationService {

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
}
