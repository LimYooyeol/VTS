package com.stock.db.controller;

import com.stock.db.dto.Reply.ReplyDto;
import com.stock.db.service.MemberService;
import com.stock.db.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    private final MemberService memberService;

    @PostMapping(value = "/replies/new")
    public String newReply(
            @RequestParam int bno,
            @RequestParam String content,
            Principal principal
    ){
        ReplyDto replyDto = new ReplyDto();
        replyDto.setContent(content);
        replyDto.setBno(bno);
        replyDto.setMno(memberService.getMno(principal.getName().toString()));

        replyService.insertReply(replyDto);

        return "redirect:/boards/" + bno;
    }

}