package com.stock.db.mapper;

import com.stock.db.domain.MemberVO;
import com.stock.db.dto.Member.ChangeNicknameDto;
import com.stock.db.dto.Member.MemberNickNameDto;
import com.stock.db.dto.Member.MemberSignUpDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /*
        @brief  : 상승률 상위 3개 종목을 모두 보유하고 있는 회원 조회
        @detail : num 명 이상일 경우 보유자산(deposit)이 많은 경우가 우위
        @return : 상승률 상위 3개 종목을 모두 보유하고 있는 회원의 목록
        @param  :
            num : 조회할 회원의 수
     */
    public List<MemberNickNameDto> getTopUsers(int num);

    public int findMnoByUserId(String userId);

    void changeNickname(ChangeNicknameDto changeNicknameDto);
}
