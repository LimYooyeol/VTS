package com.stock.db.service;

import com.stock.db.domain.MemberVO;
import com.stock.db.dto.Member.MemberSignUpDto;
import com.stock.db.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberMapper memberMapper;


    @Test
    @DisplayName("회원가입 성공")
    public void 회원가입_테스트() throws Exception{
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto();
        setTestMemberSignUpDto(memberSignUpDto);

        //when
        int mno = memberService.insertMember(memberSignUpDto);

        //then
        MemberVO findMember = memberMapper.findById(memberSignUpDto.getId());

        assertNotNull(findMember); // 회원 가입 성공
        assertEquals(mno, memberMapper.findById(memberSignUpDto.getId()).getMno()); // 가입한 회원의 mno 가 return
    }


    public static void setTestMemberSignUpDto(MemberSignUpDto memberSignUpDto){
        memberSignUpDto.setId("test");
        memberSignUpDto.setPwd("1234");
        memberSignUpDto.setMname("홍길동");
        memberSignUpDto.setNickname("테스트 계정");
        memberSignUpDto.setBirthDate(LocalDate.of(2000, 1, 1));
        memberSignUpDto.setEmail("test@test");
        memberSignUpDto.setPhone("010-1234-1234");
        memberSignUpDto.setAddress("123-123");
        memberSignUpDto.setAccount("1000-1000");
    }
}