package com.stock.db.controller;

import com.stock.db.dto.Board.BoardCriteria;
import com.stock.db.dto.Board.BoardDetailDto;
import com.stock.db.dto.Board.BoardWriteDto;
import com.stock.db.service.BoardService;
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

    @GetMapping(value = "/boards")
    public String boardList(
            @RequestParam(required = false, defaultValue = "0") int category,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) String cno,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String writer,
            Model model, Principal principal, HttpServletRequest request
    ){
        BoardCriteria boardCriteria = new BoardCriteria(category, page, 10, cno, title, writer);
        List<BoardDetailDto> boards = boardService.getPage(boardCriteria);

        if(boards.size() == 0){ // 존재하지 않는 페이지를 입력하는 경우 이전 페이지로 redirect
            return "redirect:" + request.getHeader("Referer");
        }
        int maxPageNum = boardService.getMaxPageNum(boardCriteria);


        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
        }
        model.addAttribute("boards", boards);
        model.addAttribute("boardCriteria", boardCriteria);
        model.addAttribute("max_page_num", maxPageNum);

        return "/boardList";
    }

    @GetMapping("/boards/new")
    public String writeBoardRequest(Model model){
        model.addAttribute("board_write_form" , new BoardWriteDto());
        return "/board/writeBoard";
    }

    @PostMapping("/boards")
    public String writeBoard(BoardWriteDto boardWriteDto){
        int bno = boardService.insertBoard(boardWriteDto);

        return "/boards/" + bno;
    }

    @GetMapping("/boards/{bno}")
    public String boardDetail(@PathVariable int bno, Model model, HttpServletRequest httpServletRequest){
        BoardDetailDto boardDetail = boardService.findByBno(bno);

        if(boardDetail == null){    // 존재하지 않는 게시물 번호를 요청하는 경우
            return "redirect:" + httpServletRequest.getHeader("Referer");
        }

        model.addAttribute("board_detail", boardDetail);

        return "/boards/detail";
    }

}
