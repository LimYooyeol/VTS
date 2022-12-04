package com.stock.db.service;

import com.stock.db.domain.ReplyVO;
import com.stock.db.dto.Reply.ReplyDto;
import com.stock.db.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyMapper replyMapper;


    /*
        @brief  : 댓글 추가
        @return : 댓글 번호
        @param  :
            replyDto    : 댓글 작성 DTO
     */
    public int insertReply(ReplyDto replyDto){
        int updated = replyMapper.insertReply(replyDto);

        return replyDto.getRno();
    }

    /*
        @brief  : 댓글 삭제
        @return : 업데이트한 행이 수
        @param  :
            rno : 댓글 번호
     */
    public int deleteReply(int rno){
        return replyMapper.deleteReply(rno);
    }

    /*
        @brief  : 게시물에 작성된 댓글 조회
        @return : 게시물에 달린 댓글 목록
        @param  :
            bno : 게시물 번호
     */
    @Transactional(readOnly = true)
    public List<ReplyVO> findRepliesByBno(int bno){
        return replyMapper.findRepliesByBno(bno);
    }

    /*
        @brief  : 댓글번호로 댓글 조회
        @return : 조회한 댓글
        @parma  :
            rno : 댓글번호
     */
    @Transactional(readOnly = true)
    public ReplyVO findByRno(int rno){
        return replyMapper.findByRno(rno);
    }

}
