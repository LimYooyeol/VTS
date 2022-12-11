package com.stock.db.controller;

import com.stock.db.domain.CorporationVO;
import com.stock.db.domain.MemberVO;
import com.stock.db.dto.Corporation.CorporationCriteria;
import com.stock.db.dto.Member.ChangeNicknameDto;
import com.stock.db.service.CorporationService;
import com.stock.db.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    private final CorporationService corporationService;

    @GetMapping(value = "/admin/corporations/new")
    public String newCorporation(Model model){
        return "admin/add_corporation";
    }

    @PostMapping(value = "/admin/corporations/delete")
    public String deleteCorp(
            @RequestParam String cno
    ){
        corporationService.deleteCorporation(cno);

        return "redirect:/admin/corporations";
    }

    @GetMapping(value = "/admin/corporations")
    public String searchCorporation(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "0") int sorting,
            @RequestParam(required = false, defaultValue = "-50") int min,
            @RequestParam(required = false, defaultValue = "50") int max,
            @RequestParam(required = false) String cname,
            Model model, Principal principal, HttpServletRequest request
    ){
        if(page < 1){
            return "redirect:" + request.getHeader("Referer");
        }

        CorporationCriteria criteria = new CorporationCriteria(page, 5, sorting, cname, min, max);
        int maxPageNum = corporationService.getMaxPageNum(criteria);
        if( page > maxPageNum){
            return "redirect:" + request.getHeader("Referer");
        }
        List<CorporationVO> corpList = corporationService.getPage(criteria);

        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
        }

        model.addAttribute("corp_list", corpList);
        model.addAttribute("criteria", criteria);
        model.addAttribute("max_page_num", maxPageNum);

        return "admin/corporation";
    }

    @GetMapping(value = "/admin/members")
    public String memberList(
            Principal principal,
            Model model,
            @RequestParam(required = false) String searchId
    ){
        model.addAttribute("user_id", principal.getName().toString());

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
