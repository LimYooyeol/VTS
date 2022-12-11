package com.stock.db.controller;

import com.stock.db.domain.MemberVO;
import com.stock.db.dto.Member.ChangeNicknameDto;
import com.stock.db.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping(value = "/admin/members")
    public String memberList(
            Principal principal,
            Model model,
            @RequestParam(required = false) String searchId
    ){
        if(searchId != null) {
            List<MemberVO> members = memberService.findMembers(searchId);
            model.addAttribute("members", members);
        }
        else{
            model.addAttribute("members", memberService.findMembers());
        }

        return "admin/member";
    }

    @PostMapping(value = "/admin/members/delete")
    public String deleteMember(
            @RequestParam int mno){
        memberService.deleteMember(mno);

        return "redirect:/admin/members";
    }

    @GetMapping(value = "admin/members/details/{mno}")
    public String memberDetails(
            @PathVariable int mno,
            Model model
    ){
        MemberVO member = memberService.findByMno(mno);

        model.addAttribute("member", member);

        return "admin/modify_member";
    }

    @PostMapping(value = "/admin/members/changeNickname")
    public String changeNickname(
            @RequestParam int mno,
            @RequestParam String nickname
    ){
        ChangeNicknameDto changeNicknameDto = new ChangeNicknameDto();
        changeNicknameDto.setMno(mno);
        changeNicknameDto.setNickname(nickname);

        memberService.changeNickname(changeNicknameDto);

        return "redirect:/admin/members/details/" + mno;
    }

}
