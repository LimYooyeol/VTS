package com.stock.db.controller;

import com.stock.db.dto.Possesses.PossessesDetailDto;
import com.stock.db.mapper.PossessesMapper;
import com.stock.db.service.MemberService;
import com.stock.db.service.PossessesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PossessesController {

    private final PossessesService possessesService;
    private final MemberService memberService;

    @GetMapping(value = "/members/possession")
    public String possessionList(
            Model model,
            Principal principal
    ){
        String userId = principal.getName().toString();
        long deposit = memberService.checkDeposit(userId);

        List<PossessesDetailDto> possesses = possessesService.findPossessesByUserId(userId);

        model.addAttribute("user_id", principal.getName());
        model.addAttribute("possesses", possesses);
        model.addAttribute("deposit", deposit);

        return "";
    }
}
