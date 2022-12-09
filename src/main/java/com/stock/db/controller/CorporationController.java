package com.stock.db.controller;

import com.stock.db.domain.CorporationVO;
import com.stock.db.domain.HoldersVO;
import com.stock.db.dto.Corporation.CorporationCriteria;
import com.stock.db.service.CorporationService;
import com.stock.db.service.HoldersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CorporationController {
    private final CorporationService corporationService;
    private final HoldersService holdersService;

    @GetMapping(value = "/corporations/search")
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

        return "stock/stock";
    }

    @GetMapping(value = "/corporations/market/{cno}")
    public String corporationMarket(
            @PathVariable(required = true) String cno,
            Model model, Principal principal
    ){
        CorporationVO corp = corporationService.findByCno(cno);
        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
        }

        model.addAttribute("corp", corp);

        return "stock/market";
    }

    @GetMapping(value = "/corporations/details/{cno}")
    public String corporationDetail(
            @PathVariable String cno,
            Model model, HttpServletRequest request, Principal principal
    ){
        CorporationVO corp = corporationService.findByCno(cno);
        List<HoldersVO> holders = holdersService.getHolderListByCno(cno);

        if(corp == null){
            return "redirect:" + request.getHeader("Referer");
        }

        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
        }
        model.addAttribute("corp", corp);
        model.addAttribute("holders", holders);

        return "corporation/detail";
    }

}
