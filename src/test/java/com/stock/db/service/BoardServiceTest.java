package com.stock.db.service;

import com.stock.db.domain.BoardVO;
import com.stock.db.dto.Board.BoardPreviewDto;
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
        setBoardDto(boardWriteDto, mno, cno, 0);

        // when
        int bno = boardService.insertBoard(boardWriteDto);
        BoardVO findBoard = boardService.findByBno(bno);

        // then
        assertEquals(bno, boardWriteDto.getBno());
        assertNotNull(findBoard);
    }

    @Test
    @DisplayName("게시물 작성 및 조회")
    public void 게시물_작성_및_조회2(){
        // given
        int mno = createTestMember();
        String cno = null;      // 삼성전자

        BoardWriteDto boardWriteDto = new BoardWriteDto();
        setBoardDto(boardWriteDto, mno, cno, 0);

        // when
        int bno = boardService.insertBoard(boardWriteDto);
        BoardVO findBoard = boardService.findByBno(bno);

        // then
        assertEquals(bno, boardWriteDto.getBno());
        assertNotNull(findBoard);
        System.out.println(findBoard);
    }

    /*
        @warning : DB에 저장된 게시물에 따라 결과 다름
                   (국내 주식 게시물 1개, 기타 게시물 1개인 경우에만 유효)
     */
    @Test
    public void 페이지_조회(){
        // given
        int insertNum1 = 8, insertNum2 = 2;
        int mno = createTestMember();
        String cno = "005930";

        insertDomesticStockBoards(mno, cno, insertNum1);
        insertEtcBoards(mno, insertNum2);

        Criteria criteria1 = new Criteria(0,2, 5);
        Criteria criteria2 = new Criteria(1, 2, 5);
        Criteria criteria3 = new Criteria(2, 1, 5);

        // when
        List<BoardPreviewDto> page1 = boardService.getPage(criteria1);
        List<BoardPreviewDto> page2 = boardService.getPage(criteria2);
        List<BoardPreviewDto> page3 = boardService.getPage(criteria3);

        // then
        assertNotNull(page1);
        assertEquals(5, page1.size());

        assertNotNull(page2);
        assertEquals(4, page2.size());

        assertNotNull(page3);
        assertEquals(3, page3.size());
    }

    @Test
    public void 검색_조건_테스트(){
        // given
        Criteria criteria = new Criteria(0, 1,  5);
        criteria.setCno("005930");
        criteria.setTitle(null);

        // when
        List<BoardPreviewDto> page = boardService.getPage(criteria);

        // then
        assertNotNull(page);
        assertEquals(1, page.size());
        assertEquals("005930", page.get(0).getCno());

        System.out.println(page.get(0));
    }

    @Test
    public void 검색_조건_테스트2(){
        // given
        int mno = createTestMember();
        insertEtcBoards(mno, 3);
        insertDomesticStockBoards(mno, "005930", 5);

        Criteria criteria = new Criteria(2, 1, 5);
        criteria.setCno(null);
        criteria.setTitle("6");

        // when
        List<BoardPreviewDto> page = boardService.getPage(criteria);

        // then
        assertEquals(0, page.size());
    }
    @Test
    public void 검색_조건_테스트3(){
        // given
        int mno = createTestMember();
        insertEtcBoards(mno, 3);
        insertDomesticStockBoards(mno, "005930", 5);

        Criteria criteria = new Criteria(2, 1, 5);
        criteria.setCno(null);
        criteria.setWriter("스트");

        // when
        List<BoardPreviewDto> page = boardService.getPage(criteria);

        // then
        assertEquals(3, page.size());
    }

    @Test
    public void 페이지_수_계산_테스트(){
        // given
        int mno = createTestMember();
        insertEtcBoards(mno, 4);
        insertDomesticStockBoards(mno, "005930", 10);
        Criteria criteria = new Criteria(1, 1, 5, null, null, null);

        // when
        int maxPageNum = boardService.getMaxPageNum(criteria);

        // then
        assertEquals(3, maxPageNum);

    }

    private void insertEtcBoards(int mno, int insertNum) {
        for(int i = 0 ; i <insertNum ; i++){
            BoardWriteDto boardWriteDto = new BoardWriteDto();
            setBoardDto(boardWriteDto, mno, null, i + 1);
            boardService.insertBoard(boardWriteDto);
        }
    }

    public int createTestMember(){
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto();
        setTestMemberSignUpDto(memberSignUpDto);
        int mno = memberService.insertMember(memberSignUpDto);

        return mno;
    }

    public void setBoardDto(BoardWriteDto boardWriteDto, int mno, String cno, int num){
        boardWriteDto.setTitle("테스트 게시물#" + num);
        boardWriteDto.setContent("테스트 게시물 내용");

        boardWriteDto.setMno(mno);
        boardWriteDto.setCno(cno);
    }

    public void insertDomesticStockBoards(int mno, String cno, int boardNum){

        for(int i = 0 ; i <boardNum; i++){
            BoardWriteDto boardWriteDto = new BoardWriteDto();
            setBoardDto(boardWriteDto, mno, cno, i + 1);
            boardService.insertBoard(boardWriteDto);
        }
    }

    public boolean is_sorted(List<BoardPreviewDto> findBoards){
        for(int i = 0; i < findBoards.size() - 1; i++){
            if(findBoards.get(i).getBno() < findBoards.get(i + 1).getBno() ){
                return false;
            }
        }
        return true;
    }
}