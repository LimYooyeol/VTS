package com.stock.db.service;

import com.stock.db.domain.BoardVO;
import com.stock.db.domain.ReplyVO;
import com.stock.db.dto.Board.BoardWriteDto;
import com.stock.db.dto.Member.MemberSignUpDto;
import com.stock.db.dto.Reply.ReplyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.stock.db.service.MemberServiceTest.setTestMemberSignUpDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplyServiceTest {
    @Autowired MemberService memberService;
    @Autowired ReplyService replyService;
    @Autowired BoardService boardService;

    @Test
    public void 댓글_작성_테스트() {
        // given
        int mno = createTestMember();
        String cno = "005930";
        int bno = createTestBoard(mno, cno);

        ReplyDto replyDto = new ReplyDto();
        replyDto.setBno(bno);
        replyDto.setMno(mno);
        replyDto.setContent("테스트 댓글");

        // when
        int rno = replyService.insertReply(replyDto);
        ReplyVO findReply = replyService.findByRno(rno);

        // then
        assertNotNull(findReply);
    }

    @Test
    public void 댓글_작성_테스트2(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int bno = createTestBoard(mno, cno);

        ReplyDto replyDto = new ReplyDto();
        replyDto.setBno(bno);
        replyDto.setMno(mno);
        replyDto.setContent("테스트 댓글");

        ReplyDto replyDto2 = new ReplyDto();
        replyDto2.setBno(bno);
        replyDto2.setMno(mno);
        replyDto2.setContent("테스트 댓글2");

        // when
        replyService.insertReply(replyDto);
        replyService.insertReply(replyDto2);
        List<ReplyVO> findReplies = replyService.findRepliesByBno(bno);

        // then
        assertNotNull(findReplies);
        assertEquals(2, findReplies.size());
    }

    @Test
    public void 댓글_삭제_테스트(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int bno = createTestBoard(mno, cno);

        ReplyDto replyDto = new ReplyDto();
        replyDto.setBno(bno);
        replyDto.setMno(mno);
        replyDto.setContent("테스트 댓글");

        int rno = replyService.insertReply(replyDto);

        // when
        replyService.deleteReply(rno);
        List<ReplyVO> replies = replyService.findRepliesByBno(bno);
        ReplyVO reply = replyService.findByRno(rno);

        // then
        assertEquals(0, replies.size());
        assertNull(reply);
    }

    @Test
    public void 댓글_삭제_전이_테스트(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int bno = createTestBoard(mno, cno);

        ReplyDto replyDto = new ReplyDto();
        replyDto.setBno(bno);
        replyDto.setMno(mno);
        replyDto.setContent("테스트 댓글");

        ReplyDto replyDto2 = new ReplyDto();
        replyDto2.setBno(bno);
        replyDto2.setMno(mno);
        replyDto2.setContent("테스트 댓글2");

        replyService.insertReply(replyDto);
        replyService.insertReply(replyDto2);

        int prev = replyService.findRepliesByBno(bno).size();

        // when
        boardService.deleteBoard(bno);
        BoardVO findBoard = boardService.findByBno(bno);
        List<ReplyVO> replies = replyService.findRepliesByBno(bno);

        // then
        assertEquals(2, prev);
        assertNull(findBoard);
        assertEquals(0, replies.size());
    }

    public void setBoardDto(BoardWriteDto boardWriteDto, int mno, String cno){
        boardWriteDto.setTitle("테스트 게시물");
        boardWriteDto.setContent("테스트 게시물 내용");

        boardWriteDto.setMno(mno);
        boardWriteDto.setCno(cno);
    }
    private int createTestBoard(int mno, String cno) {
        BoardWriteDto boardWriteDto = new BoardWriteDto();
        setBoardDto(boardWriteDto, mno, cno);

        return boardService.insertBoard(boardWriteDto);
    }

    public int createTestMember(){
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto();
        setTestMemberSignUpDto(memberSignUpDto);
        int mno = memberService.insertMember(memberSignUpDto);

        return mno;
    }

}