package com.stock.db.mapper;

import com.stock.db.domain.BoardVO;
import com.stock.db.dto.Board.BoardPreviewDto;
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
    public BoardVO findByBno(int Bno);

    /*
        @brief  : 게시물 작성
        @return : 업데이트한 행의 개수
        @param  :
            boardWriteDto : 게시물 내용
     */
    public int insertBoard(BoardWriteDto boardWriteDto);

    /*
        @brief  : 게시물 삭제
        @return : 업데이트한 행의 개수
        @param  :
            bno : 게시물 번호
     */
    public int deleteBoard(int bno);

    /*
        @brief  : 게시물 목록(페이지) 조회
        @return : 조회한 게시물 목록
        @param  :
            criteria : 검색 정보(카테고리, 페이지 번호, 페이지 당 게시물 수 등)
     */
    public List<BoardPreviewDto> getBoards(Criteria criteria);

    /*
        @brief  : 해당 검색 조건에서 가능한 페이지 최대 값 조회
        @return : 해당 검색 조건에서 가능한 페이지 최대 값
        @param  :
            criteria : 검색 정보(카테고리, 페이지 번호 등)
     */
    public int getMaxPageNum(Criteria criteria);

    /*
        @brief  : 최근 게시물 조회
        @return : 최근 num 개의 게시물
        @param  :
            num : 최근 몇 개의 게시물을 볼 것인지
     */
    public List<BoardVO> getNewBoards(int num);

}
