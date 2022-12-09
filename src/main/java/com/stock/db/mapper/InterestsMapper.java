package com.stock.db.mapper;

import com.stock.db.domain.CorporationVO;
import com.stock.db.domain.InterestsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterestsMapper {

    /*
        @brief  : 관심종목 추가
        @return : 업데이트 한 행의 수
        @param  :
            mno     : 회원번호
            gname   : 관심그룹 이름
            cno     : 기업코드
    */
    public int insertInterests(int mno, String gname, String cno);


    /*
        @brief  : 관심종목 삭제
        @return : 업데이트 한 행의 수
        @param  :
            mno     : 회원번호
            gname   : 관심그룹 이름
            cno     : 기업코드
     */
    public int deleteInterests(int mno, String gname, String cno);

    /*
        @brief  : 관심그룹에 속한 관심종목들의 목록 조회
        @return : 관심종목 목록
        @parma  :
            mno     : 회원번호
            gname   : 관심그룹 이룸
     */
    public List<CorporationVO> findInterestsList(int mno, String gname);

    public List<String> findInterestsGroups(int mno);
}
