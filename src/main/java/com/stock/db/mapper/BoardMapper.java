package com.stock.db.mapper;

import com.stock.db.domain.BoardVO;
import com.stock.db.dto.Board.BoardWriteDto;
import com.stock.db.dto.Board.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    /*
        @brief  : 게시물 번호로 게시물 조회
        @return : 조회한 게시물
        @param  :
            bno : 조회할 게시물 번호
     */
    BoardVO findByBno(int Bno);

    /*
        @brief  : 게시물 작성
        @return : 업데이트한 행의 개수
        @param  :
            boardWriteDto : 게시물 내용
     */
    int insertBoard(BoardWriteDto boardWriteDto);

    /*
        @brief  : 게시물 목록(페이지) 조회
        @return : 조회한 게시물 목록
        @param  :
            criteria : 페이징 정보(페이지 번호, 페이지 당 게시물 수)
     */
    List<BoardVO> getPage(Criteria criteria);

}
