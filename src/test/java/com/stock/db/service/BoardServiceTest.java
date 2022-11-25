package com.stock.db.service;

import com.stock.db.domain.BoardVO;
import com.stock.db.dto.Board.BoardWriteDto;
import com.stock.db.dto.Board.Criteria;
import com.stock.db.dto.Member.MemberSignUpDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.stock.db.service.MemberServiceTest.setTestMemberSignUpDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired MemberService memberService;
    @Autowired BoardService boardService;



    @Test
    @DisplayName("게시물 작성 및 조회")
    public void 게시물_작성_및_조회(){
        // given
        int mno = createTestMember();
        String cno = "005930";      // 삼성전자

        BoardWriteDto boardWriteDto = new BoardWriteDto();
        setBoardDto(boardWriteDto, mno, cno);

        // when
        int bno = boardService.insertBoard(boardWriteDto);
        BoardVO findBoard = boardService.findByBno(bno);

        // then
        assertNotNull(findBoard);
        assertEquals(bno, boardWriteDto.getBno());
    }

    @Test
    public void 페이지_조회(){
        // given
        int insertNum = 5;
        int mno = createTestMember();
        String cno = "005930";

        insertBoards(mno, cno, insertNum);
        Criteria criteria = new Criteria(1, 5);

        // when
        List<BoardVO> findBoards = boardService.getPage(criteria);

        // then
        assertNotNull(findBoards);
        assertEquals(insertNum, findBoards.size());
        assertTrue(is_sorted(findBoards));
    }

    public int createTestMember(){
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto();
        setTestMemberSignUpDto(memberSignUpDto);
        int mno = memberService.insertMember(memberSignUpDto);

        return mno;
    }

    public void setBoardDto(BoardWriteDto boardWriteDto, int mno, String cno){
        boardWriteDto.setTitle("테스트 게시물");
        boardWriteDto.setContent("테스트 게시물 내용");

        boardWriteDto.setMno(mno);
        boardWriteDto.setCno(cno);
    }

    public void insertBoards(int mno, String cno, int boardNum){
        BoardWriteDto boardWriteDto = new BoardWriteDto();
        setBoardDto(boardWriteDto, mno, cno);

        for(int i = 0 ; i <boardNum; i++){
            boardService.insertBoard(boardWriteDto);
        }
    }

    public boolean is_sorted(List<BoardVO> findBoards){
        for(int i = 0; i < findBoards.size() - 1; i++){
            if(findBoards.get(i).getBno() < findBoards.get(i + 1).getBno() ){
                return false;
            }
        }
        return true;
    }
}