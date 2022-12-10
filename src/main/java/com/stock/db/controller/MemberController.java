package com.stock.db.controller;

import com.stock.db.domain.CorporationVO;
import com.stock.db.domain.InterestsVO;
import com.stock.db.domain.MemberVO;
import com.stock.db.dto.Corporation.CorporationBriefDto;
import com.stock.db.dto.Member.ChangeNicknameDto;
import com.stock.db.dto.Member.MemberSignUpDto;
import com.stock.db.dto.Orders.OrdersDetailDto;
import com.stock.db.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final InterestGroupService interestGroupService;

    private final OrdersService ordersService;

    private final InterestsService interestsService;

    private final CorporationService corporationService;

    @GetMapping("/login")
    public String memberLogin(){
        return "/member/loginForm";
    }

    @PostMapping(value = "/members/changeNickname")
    public String changeNickname(
            @RequestParam int mno,
            @RequestParam String nickname
    ){

        ChangeNicknameDto changeNicknameDto = new ChangeNicknameDto();
        changeNicknameDto.setMno(mno);
        changeNicknameDto.setNickname(nickname);

        memberService.changeNickname(changeNicknameDto);

        return "redirect:/members/mypage";
    }

    @GetMapping("/members/mypage")
    public String myPage(Principal principal, Model model){
        MemberVO member = memberService.findById(principal.getName().toString());

        model.addAttribute("user_id", principal.getName().toString());
        model.addAttribute("member", member);

        return "mypage/mypage";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("msg", "로그인 실패");

        return "/member/loginForm";
    }

    @GetMapping("/signup")
    public String memberSignUp(Model model){

        model.addAttribute("memberSignUpDto", new MemberSignUpDto());

        return "/member/signUpForm";
    }

    @PostMapping("/signup")
    public String memberSingUp(MemberSignUpDto memberSignUpDto, Model model){

        try{
            int mno = memberService.insertMember(memberSignUpDto);
        }
        catch (DataIntegrityViolationException e){  // 증복
            return "/member/signupForm";
        }
        catch (IllegalStateException e){            //
            model.addAttribute("errorMessage", e.getMessage());
            return "/member/signupForm";
        }

        return "redirect:/";
    }

    @GetMapping("/members/interests")
    public String favoriteList(
            Model model,
            Principal principal,
            @RequestParam(required = false) String gname
    ){
        model.addAttribute("user_id", principal.getName().toString());
        int mno = memberService.getMno(principal.getName().toString());
        List<String> groups = interestGroupService.findInterestGroupsByMno(mno);

        if(gname == null && groups.size() == 0) {
            model.addAttribute("msg", "아직 관심그룹이 없습니다.");
            return "mypage/favorite";
        }

        List<CorporationVO> interestsList = null;
        if(gname == null && groups.size() != 0){
            String initGroup = groups.get(0);
            interestsList = interestsService.findInterestsList(mno, initGroup);
            gname = initGroup;
        }

        if(gname != null){
            interestsList = interestsService.findInterestsList(mno, gname);
        }


        List<CorporationBriefDto> corps = corporationService.getCnames();

        model.addAttribute("corps", corps);
        model.addAttribute("current_group", gname);
        model.addAttribute("interests", interestsList);
        model.addAttribute("groups", groups);

        return "mypage/favorite";
    }

    @PostMapping(value = "/members/interests/delete")
    public String deleteInterests(
            @RequestParam String cno,
            @RequestParam String gname,
            Principal principal, RedirectAttributes redirectAttributes){
        int mno = memberService.getMno(principal.getName().toString());
        interestsService.deleteInterests(mno, gname, cno);
        redirectAttributes.addAttribute("gname", gname);

        return "redirect:/members/interests";
    }


    @PostMapping(value = "/members/interests/new")
    public String newInterests(
            @RequestParam String cno,
            @RequestParam String gname,
            Principal principal, RedirectAttributes redirectAttributes
    ){
        int mno = memberService.getMno(principal.getName().toString());
        interestsService.insertInterests(mno, gname, cno);
        redirectAttributes.addAttribute("gname", gname);

        return "redirect:/members/interests";
    }

    @PostMapping(value = "/members/interests_group/delete")
    public String deleteInterestsGroup(
            @RequestParam String gname,
            Principal principal)
    {
        int mno = memberService.getMno(principal.getName().toString());
        interestGroupService.deleteInterestGroup(mno, gname);

        return "redirect:/members/interests";
    }

    @PostMapping(value = "/members/interests_group/new")
    public String newInterestsGroup(
            @RequestParam(required = true) String gname,
            Principal principal
    ){
        int mno = memberService.getMno(principal.getName().toString());
        interestGroupService.insertInterestGroup(mno, gname);

        return "redirect:/members/interests";
    }

    @GetMapping("/members/orders")
    public String orderList(Model model, Principal principal){
        int mno = memberService.getMno(principal.getName().toString());

        List<OrdersDetailDto> orders = ordersService.getOrdersByMno(mno);

        model.addAttribute("user_id", principal.getName().toString());
        model.addAttribute("orders", orders);

        return "mypage/history";
    }


}
