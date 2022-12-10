package com.stock.db.service;

import com.stock.db.domain.InterestGroupVO;
import com.stock.db.mapper.InterestGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestGroupService {
    private final InterestGroupMapper interestGroupMapper;

    /*
        @brief  : 관심그룹 추가
        @return : 업데이트한 행의 수
        @param  :
            mno     : 회원 번호
            gname   : 그룹 이름
     */
    public int insertInterestGroup(int mno, String gname){
        int updated = interestGroupMapper.insertInterestGroup(mno, gname);

        return updated;
    }

    /*
        @brief  : 관심그룹 삭제
        @return : 업데이트한 행의 수
        @param  :
            mno     : 회원 번호
            gname   : 그룹 이름
     */
    public int deleteInterestGroup(int mno, String gname){
        int updated = interestGroupMapper.deleteInterestGroup(mno, gname);

        return updated;
    }

    /*
        @brief  : 관심그룹 검색
        @return : 검색한 그룹
        @param  :
            mno     : 회원 번호
            gname   : 그룹 이름
     */
    @Transactional(readOnly = true)
    public InterestGroupVO findInterestGroup(int mno, String gname){
        return interestGroupMapper.findInterestGroup(mno, gname);
    }

    @Transactional(readOnly = true)
    public List<String> findInterestGroupsByMno(int mno){
        return interestGroupMapper.findInterestGroupsByMno(mno);
    }
}
