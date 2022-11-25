package com.stock.db.service;

import com.stock.db.domain.BoardVO;
import com.stock.db.dto.Board.BoardWriteDto;
import com.stock.db.dto.Board.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardService {

    /*
        @brief  : 게시물 작성
        @return : 작성한 게시물 번호
        @param  :
            boardWriteDto : 게시물 작성 관련 정보
     */
    public int insertBoard(BoardWriteDto boardWriteDto){

        return 0;
    }

    /*
        @brief  : 게시물 목록(페이지) 조회
        @return : 조회한 게시물 목록
        @param  :
            criteria : 페이징 정보(페이지 번호, 페이지 당 게시물 수)
     */
    @Transactional(readOnly = true)
    public List<BoardVO> getPage(Criteria criteria){

        return null;
    };

    /*
        @brief  : 게시물 번호로 조회
        @return : 조회한 게시물
        @param  :
            bno : 게시물 번호
     */
    @Transactional(readOnly = true)
    public BoardVO findByBno(int bno){
        return null;
    };

}
