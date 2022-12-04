package com.stock.db.service;

import com.stock.db.domain.InterestGroupVO;
import com.stock.db.dto.Member.MemberSignUpDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.stock.db.service.MemberServiceTest.setTestMemberSignUpDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InterestGroupServiceTest {

    @Autowired InterestGroupService interestGroupService;
    @Autowired MemberService memberService;

    @Test
    public void 관심그룹_추가_테스트(){
        // given
        int mno = createTestMember();
        String gname = "관심그룹1";

        // when
        int updated = interestGroupService.insertInterestGroup(mno, gname);
        InterestGroupVO findGroup = interestGroupService.findInterestGroup(mno, gname);

        // then
        assertEquals(1, updated);
        assertNotNull(findGroup);
    }

    @Test
    public void 관심그룹_삭제_테스트(){
        // given
        int mno = createTestMember();
        String gname = "관심그룹1";
        interestGroupService.insertInterestGroup(mno, gname);

        // when
        int updated = interestGroupService.deleteInterestGroup(mno, gname);
        InterestGroupVO findGroup = interestGroupService.findInterestGroup(mno, gname);

        // then
        assertEquals(1, updated);
        assertNull(findGroup);
    }


    public int createTestMember(){
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto();
        setTestMemberSignUpDto(memberSignUpDto);
        int mno = memberService.insertMember(memberSignUpDto);

        return mno;
    }

}