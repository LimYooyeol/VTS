package com.stock.db.controller;

import com.stock.db.domain.MemberVO;
import com.stock.db.dto.MemberSignUpDto;
import com.stock.db.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/login")
    public String memberLogin(){
        return "/member/loginForm";
    }

    @GetMapping("/members/signup")
    public String memberSignUp(){
        return "/member/signUpForm";
    }

    @PostMapping("/members/signup")
    public String memberSingUp(MemberSignUpDto memberSignUpDto, Model model){
        memberSignUpDto.setBeforeInsert(passwordEncoder);
        System.out.println(memberSignUpDto.toString());

        try{
            int mno = memberService.insertMember(memberSignUpDto);
//            System.out.println(mno + "===============");
        }
        catch (DataIntegrityViolationException e){  // 증복
            System.out.println("duplication =======");
            return "/member/signupForm";
        }
        catch (IllegalStateException e){            // ~
            model.addAttribute("errorMessage", e.getMessage());
//            System.out.println("err =====================");
            return "/member/signupForm";
        }

        return "redirect:/";
    }

    @GetMapping("/members/mypage")
    public String myPage(Model model, Principal principal){
        if(principal != null) {
            model.addAttribute("id", principal.getName().toString());
        }
        return "member/mypage";
    }

}
