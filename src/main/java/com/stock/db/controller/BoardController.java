package com.stock.db.controller;

import com.stock.db.dto.Board.BoardPreviewDto;
import com.stock.db.dto.Board.Criteria;
import com.stock.db.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        Criteria criteria = new Criteria(category, page, 10, cno, title, writer);
        List<BoardPreviewDto> boards = boardService.getPage(criteria);

        if(boards.size() == 0){ // 존재하지 않는 페이지를 입력하는 경우 이전 페이지로 redirect
            return "redirect:" + request.getHeader("Referer");
        }
        int maxPageNum = boardService.getMaxPageNum(criteria);


        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
        }
        model.addAttribute("boards", boards);
        model.addAttribute("criteria", criteria);
        model.addAttribute("max_page_num", maxPageNum);

        return "/boardList";
    }
}
