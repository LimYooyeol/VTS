package com.stock.db.controller;

import com.stock.db.domain.ReplyVO;
import com.stock.db.dto.Board.BoardCriteria;
import com.stock.db.dto.Board.BoardDetailDto;
import com.stock.db.dto.Board.BoardWriteDto;
import com.stock.db.dto.Corporation.CorporationBriefDto;
import com.stock.db.dto.Reply.ReplyDetailDto;
import com.stock.db.service.BoardService;
import com.stock.db.service.CorporationService;
import com.stock.db.service.MemberService;
import com.stock.db.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final MemberService memberService;
    private final CorporationService corporationService;

    private final ReplyService replyService;

    @GetMapping(value = "/boards")
    public String boardList(
            @RequestParam(required = false, defaultValue = "0") int category,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) String cno,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String writer,
            Model model, Principal principal, HttpServletRequest request
    ){
        cno = (cno == "") ? null : cno;
        title = (title == "") ? null : title;
        writer = (cno == "") ? null : writer;

        if(page < 1){
            return "redirect:" + request.getHeader("Referer");
        }
        BoardCriteria boardCriteria = new BoardCriteria(category, page, 5, cno, title, writer);
        int maxPageNum = boardService.getMaxPageNum(boardCriteria);
        if(page >  maxPageNum){ // 존재하지 않는 페이지를 입력하는 경우 이전 페이지로 redirect
            return "redirect:" + request.getHeader("Referer");
        }

        List<BoardDetailDto> boards = boardService.getPage(boardCriteria);

        List<CorporationBriefDto> corpsInfo = corporationService.getCnames();



        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
        }
        model.addAttribute("boards", boards);
        model.addAttribute("criteria", boardCriteria);
        model.addAttribute("max_page_num", maxPageNum);
        model.addAttribute("corps_info", corpsInfo);

        return "boarder/boarder";
    }

    @GetMapping("/boards/new")
    public String writeBoardRequest(Principal principal, Model model){
        List<CorporationBriefDto> corpsInfo = corporationService.getCnames();
        CorporationBriefDto etc = new CorporationBriefDto();
        etc.setCname("기타");
        etc.setCno(null);
        corpsInfo.add(0, etc);

        model.addAttribute("user_id", principal.getName().toString());
        model.addAttribute("board_write_form" , new BoardWriteDto());
        model.addAttribute("corps_info", corpsInfo);
        return "boarder/boarder_write";
    }



    @PostMapping("/boards/new")
    public String writeBoard(BoardWriteDto boardWriteDto, Principal principal){
        int mno = memberService.getMno(principal.getName().toString());

        boardWriteDto.setMno(mno);
        if(boardWriteDto.getCno().equals("")){
            boardWriteDto.setCno(null);
        }

        int bno = boardService.insertBoard(boardWriteDto);

        return "redirect:" + "/boards/" + bno;
    }

    @GetMapping(value = "/boards/update")
    public String updateBoardRequest(
            @RequestParam int bno,
            Principal principal,
            Model model
    ){
        String userId = principal.getName();
        BoardDetailDto board = boardService.findByBno(bno);

        List<CorporationBriefDto> corps = corporationService.getCnames();

        model.addAttribute("corps_info", corps);
        model.addAttribute("board_write_form", new BoardWriteDto());
        model.addAttribute("user_id", principal.getName().toString());
        model.addAttribute("board", board);
        model.addAttribute("bno", bno);

        return "/boarder/boarder_write";
    }

    @PostMapping(value = "/boards/delete")
    public String deleteBoard(
            @RequestParam int bno,
            Principal principal,
            HttpServletRequest request

    ){
        String userId = principal.getName().toString();
        if(memberService.getMno(userId) != boardService.findByBno(bno).getMno()){
            return "redirect:" + request.getHeader("Referer");
        }

        boardService.deleteBoard(bno);

        return "redirect:/boards";
    }

    @PostMapping(value = "/boards/update")
    public String updateBoard(
            BoardWriteDto boardWriteDto
    ){
        int bno = boardWriteDto.getBno();
        boardService.updateBoard(boardWriteDto);

        return "redirect:/boards/" + bno;
    }

    @GetMapping("/boards/{bno}")
    public String boardDetail(@PathVariable int bno,
                              Principal principal, Model model, HttpServletRequest httpServletRequest){
        BoardDetailDto boardDetail = boardService.findByBno(bno);

        if(boardDetail == null){    // 존재하지 않는 게시물 번호를 요청하는 경우
            return "redirect:" + httpServletRequest.getHeader("Referer");
        }

        List<ReplyDetailDto> replies = replyService.findRepliesByBno(bno);

        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
            int mno = memberService.getMno(principal.getName().toString());
            model.addAttribute("user_mno", mno);
        }

        model.addAttribute("board_detail", boardDetail);
        model.addAttribute("replies", replies);
        model.addAttribute("bno", bno);

        return "boarder/boarder_read";
    }

}
