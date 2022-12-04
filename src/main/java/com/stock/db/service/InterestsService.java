package com.stock.db.service;

import com.stock.db.domain.InterestsVO;
import com.stock.db.mapper.InterestsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestsService {

    private final InterestsMapper interestsMapper;

    /*
        @brief  : 관심종목 추가
        @return : 업데이트 한 행의 수
        @param  :
            mno     : 회원번호
            gname   : 관심그룹 이름
            cno     : 기업코드
    */
    public int insertInterests(int mno, String gname, String cno){
        int updated = interestsMapper.insertInterests(mno, gname, cno);

        return updated;
    }


    /*
        @brief  : 관심종목 삭제
        @return : 업데이트 한 행의 수
        @param  :
            mno     : 회원번호
            gname   : 관심그룹 이름
            cno     : 기업코드
     */
    public int deleteInterests(int mno, String gname, String cno){
        int updated = interestsMapper.deleteInterests(mno, gname, cno);

        return updated;
    }

    /*
            @brief  : 관심그룹에 속한 관심종목들의 목록 조회
            @return : 관심종목 목록
            @parma  :
                mno     : 회원번호
                gname   : 관심그룹 이룸
         */
    @Transactional(readOnly = true)
    public List<InterestsVO> findInterestsList(int mno, String gname){
        return interestsMapper.findInterestsList(mno, gname);
    }

}
