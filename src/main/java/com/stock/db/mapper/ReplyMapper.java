package com.stock.db.mapper;

import com.stock.db.domain.ReplyVO;
import com.stock.db.dto.Reply.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    /*
        @brief  : 댓글 추가
        @return : 업데이트한 행의 수
        @param  :
            replyDto    : 댓글 작성 DTO
     */
    public int insertReply(ReplyDto replyDto);

    /*
        @brief  : 댓글 삭제
        @return : 업데이트한 행이 수
        @param  :
            rno : 댓글 번호
     */
    public int deleteReply(int rno);

    /*
        @brief  : 게시물에 작성된 댓글 조회
        @return : 게시물에 달린 댓글 목록
        @param  :
            bno : 게시물 번호
     */
    public List<ReplyVO> findRepliesByBno(int bno);

    /*
        @brief  : 댓글번호로 댓글 조회
        @return : 조회한 댓글
        @parma  :
            rno : 댓글번호
     */
    public ReplyVO findByRno(int rno);
}
