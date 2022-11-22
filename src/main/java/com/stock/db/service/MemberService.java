package com.stock.db.service;

import com.stock.db.domain.MemberVO;
import com.stock.db.dto.MemberSignUpDto;
import com.stock.db.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberMapper memberMapper;

    public int insertMember(MemberSignUpDto memberSignUpDto){
        memberMapper.insertMember(memberSignUpDto);

        return memberSignUpDto.getMno();
    }

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
}
