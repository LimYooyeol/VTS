package com.stock.db.service;

import com.stock.db.domain.HoldersVO;
import com.stock.db.mapper.HoldersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HoldersService {

    private final HoldersMapper holdersMapper;

    /*
        @brief  : 종목코드로 주요 주주 현황 조회
        @return : 주요 주주 목록
        @param  :
            cno : 종목 코드
     */
    @Transactional(readOnly = true)
    public List<HoldersVO> getHolderListByCno(String cno){
        return holdersMapper.getHolderListByCno(cno);
    }
}
