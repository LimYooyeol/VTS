package com.stock.db.service;

import com.stock.db.domain.InterestsVO;
import com.stock.db.dto.Member.MemberSignUpDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static com.stock.db.service.MemberServiceTest.setTestMemberSignUpDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InterestsServiceTest {
    @Autowired InterestsService interestsService;
    @Autowired InterestGroupService interestGroupService;
    @Autowired MemberService memberService;

    @Test
    public void 관심종목_추가_테스트(){
        // given
        int mno = createTestMember();
        String gname = "관심 그룹1";
        String cno = "005930";
        interestGroupService.insertInterestGroup(mno, gname);

        // when
        int updated = interestsService.insertInterests(mno, gname, cno);
        List<InterestsVO> findGroup = interestsService.findInterestsList(mno, gname);

        // then
        assertEquals(1, updated);
        assertEquals(1, findGroup.size());
        assertEquals(cno, findGroup.get(0).getCno());
    }

    @Test
    public void 관심종목_추가_테스트2(){
        // given
        int mno = createTestMember();
        String gname = "관심 그룹1";
        String cno1 = "005930";
        String cno2 = "000060";
        interestGroupService.insertInterestGroup(mno, gname);

        // when
        interestsService.insertInterests(mno, gname, cno1);
        interestsService.insertInterests(mno, gname, cno2);
        List<InterestsVO> findGroup = interestsService.findInterestsList(mno, gname);

        //
        assertEquals(2, findGroup.size());
    }

    @Test
    public void 관심종목_추가_실패_테스트(){
        // given
        int mno = createTestMember();
        String gname = "관심 그룹1";
        String cno = "000000";
        interestGroupService.insertInterestGroup(mno, gname);

        // when, then
        assertThrows(DataIntegrityViolationException.class, () -> interestsService.insertInterests(mno, gname, cno));

    }

    @Test
    public void 관심종목_삭제_테스트(){
        // given
        int mno = createTestMember();
        String gname = "관심그룹1";
        String cno = "005930";
        interestGroupService.insertInterestGroup(mno, gname);

        interestsService.insertInterests(mno, gname, cno);

        // when
        int updated = interestsService.deleteInterests(mno, gname, cno);
        List<InterestsVO> findGroup = interestsService.findInterestsList(mno, gname);

        // then
        assertEquals(1, updated);
        assertEquals(0, findGroup.size());
    }

    @Test
    public void 관심그룹_삭제_전이_테스트(){
        // given
        int mno = createTestMember();
        String gname = "관심그룹1";
        String cno1 = "005930";
        String cno2 = "000060";
        interestGroupService.insertInterestGroup(mno, gname);

        interestsService.insertInterests(mno, gname, cno1);
        interestsService.insertInterests(mno, gname, cno2);

        // when
        interestGroupService.deleteInterestGroup(mno, gname);
        List<InterestsVO> findInterests = interestsService.findInterestsList(mno, gname);

        // then
        assertEquals(0, findInterests.size());
    }


    public int createTestMember(){
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto();
        setTestMemberSignUpDto(memberSignUpDto);
        int mno = memberService.insertMember(memberSignUpDto);

        return mno;
    }

}