package com.stock.db.service;

import com.stock.db.domain.MemberVO;
import com.stock.db.dto.Member.MemberNickNameDto;
import com.stock.db.dto.Member.MemberSignUpDto;
import com.stock.db.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberMapper memberMapper;

    private final PasswordEncoder passwordEncoder;

    /*
        @brief  : 회원가입 처리
        @return : 가입한 회원의 회원번호
        @param  :
            memberSignUpDto : 회원가입 관련 정보
     */
    public int insertMember(MemberSignUpDto memberSignUpDto){
        memberSignUpDto.setBeforeInsert(passwordEncoder);
        memberMapper.insertMember(memberSignUpDto);

        return memberSignUpDto.getMno();
    }

    /*
        @brief  : 회원 ID로 회원정보 조회, security 로직에서 사용 (UserDetailsService의 구현체)
        @return : 회원 정보(권한 포함)를 담은 User 객체
        @param  :
            id : 회원 ID
     */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        MemberVO memberVO = memberMapper.findById(id);

        if(memberVO == null){
            throw new UsernameNotFoundException(id);
        }

        String role = (memberVO.isAdmin() == true) ? "ADMIN" : "MEMBER";

        return User.builder()
                .username(memberVO.getMname())
                .password(memberVO.getPwd())
                .roles(role)
                .build();
    }


    /*
        @brief  : 상승률 상위 3개 종목을 모두 보유하고 있는 회원 조회
        @detail : num 명 이상일 경우 보유자산(deposit)이 많은 경우가 우위
        @return : 상승률 상위 3개 종목을 모두 보유하고 있는 회원의 목록
     */
    public List<MemberNickNameDto> getTopUsers(){
        int limit = 3;

        return memberMapper.getTopUsers(limit);
    }
}
