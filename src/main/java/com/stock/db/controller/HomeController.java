package com.stock.db.controller;

import com.stock.db.domain.BoardVO;
import com.stock.db.domain.CorporationVO;
import com.stock.db.dto.Corporation.SectorChangeRateDto;
import com.stock.db.dto.Member.MemberNickNameDto;
import com.stock.db.service.BoardService;
import com.stock.db.service.CorporationService;
import com.stock.db.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CorporationService corporationService;

    private final BoardService boardService;

    private final MemberService memberService;

    @GetMapping(value = "/")
    public String homePage(Model model, Principal principal){

        List<CorporationVO> topRisingCorporations = corporationService.getTopRisingCorporations(10);
        List<SectorChangeRateDto> topRisingSectors = corporationService.getTopRisingSectors(9);
        List<BoardVO> newBoards = boardService.getNewBoards();
        List<MemberNickNameDto> topUsers = memberService.getTopUsers();

        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
        }
        model.addAttribute("top_rising_corps", topRisingCorporations);
        model.addAttribute("top_rising_sectors", topRisingSectors);
        model.addAttribute("new_boards", newBoards);
        // #fix: 코스피 추가
        model.addAttribute("top_users", topUsers);

        return "index";
    }

}
