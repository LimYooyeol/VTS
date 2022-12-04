package com.stock.db.mapper;

import com.stock.db.domain.InterestGroupVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InterestGroupMapper {

    /*
        @brief  : 관심그룹 추가
        @return : 업데이트한 행의 수
        @param  :
            mno     : 회원 번호
            gname   : 그룹 이름
     */
    public int insertInterestGroup(int mno, String gname);


    /*
        @brief  : 관심그룹 삭제
        @return : 업데이트한 행의 수
        @param  :
            mno     : 회원 번호
            gname   : 그룹 이름
     */
    public int deleteInterestGroup(int mno, String gname);

    /*
        @brief  : 관심그룹 검색
        @return : 검색한 그룹
        @param  :
            mno     : 회원 번호
            gname   : 그룹 이름
     */
    public InterestGroupVO findInterestGroup(int mno, String gname);

}
