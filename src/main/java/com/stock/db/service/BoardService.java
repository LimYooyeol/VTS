package com.stock.db.service;

import com.stock.db.domain.BoardVO;
import com.stock.db.dto.Board.BoardCriteria;
import com.stock.db.dto.Board.BoardDetailDto;
import com.stock.db.dto.Board.BoardPreviewDto;
import com.stock.db.dto.Board.BoardWriteDto;
import com.stock.db.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    /*
        @brief  : 게시물 작성
        @return : 작성한 게시물 번호
        @param  :
            boardWriteDto : 게시물 작성 관련 정보
     */
    public int insertBoard(BoardWriteDto boardWriteDto){
        boardMapper.insertBoard(boardWriteDto);

        return boardWriteDto.getBno();
    }

    /*
        @brief  : 게시물 목록(페이지) 조회
        @return : 조회한 게시물 목록
        @param  :
            category : [0: 전체, 1: 국내 주식, 2: 기타]
            boardCriteria : 페이징 정보(페이지 번호, 페이지 당 게시물 수 등)
     */
    @Transactional(readOnly = true)
    public List<BoardDetailDto> getPage(BoardCriteria boardCriteria){
        List<BoardDetailDto> boards = boardMapper.getBoards(boardCriteria);
        for(BoardDetailDto b :boards){
            if(b.getCname() == null){
                b.setCname("기타");
            }
        }
        return boards;
    }

    /*
        @brief  : 게시물 번호로 조회
        @return : 조회한 게시물
        @param  :
            bno : 게시물 번호
     */
    @Transactional(readOnly = true)
    public BoardDetailDto findByBno(int bno){
        return boardMapper.findByBno(bno);
    };

    /*
        @brief  : 게시물 삭제
        @return : 업데이트한 행의 개수
        @param  :
            bno : 게시물 번호
     */
    public int deleteBoard(int bno){
        return boardMapper.deleteBoard(bno);
    }

    /*
        @brief  : 최근 게시물 조회
        @return : 최근 num 개의 게시물
        @param  :
            num : 최근 몇 개의 게시물을 볼 것인지
     */
    @Transactional(readOnly = true)
    public List<BoardPreviewDto> getNewBoardsPreview(){
        int num = 5;
        List<BoardPreviewDto> newBoardsPreview = boardMapper.getNewBoardsPreview(num);
        for(BoardPreviewDto b : newBoardsPreview){
            if(b.getCname() == null)
                b.setCname("기타");
        }

        return newBoardsPreview;
    }

    @Transactional(readOnly = true)
    public int getMaxPageNum(BoardCriteria boardCriteria){
        return boardMapper.getMaxPageNum(boardCriteria);
    }

    public void updateBoard(BoardWriteDto boardWriteDto) {
        boardMapper.updateBoard(boardWriteDto);
    }
}
