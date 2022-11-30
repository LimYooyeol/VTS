package com.stock.db.mapper;

import com.stock.db.domain.MemberVO;
import com.stock.db.dto.Member.MemberSignUpDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    /*
        @brief  : 아이디로 회원조회
        @return : 조회한 회원
        @param  :
            id  : 회원 id
     */
    public MemberVO findById(String id);

    /*
        @brief  : 회원가입 처리
        @return : 업데이트 한 행의 개수
        @param  :
            memberSignUpDto : 회원가입 관련 정보
     */
    public int insertMember(MemberSignUpDto memberSignUpDto);

    /*
        @brief  : 호원번호로 회원조회
        @return : 조회한 회원
        @param  :
            id  : 회원 번호
     */
    public MemberVO findByMno(int mno);

    /*
        @brief  : 회원 예수금 업데이트
        @return : 업데이트 한 행의 번호
        @param  :
            mno     : 회원번호
            change  : 변화액
     */
    public int updateDeposit(int mno, long change);
}
