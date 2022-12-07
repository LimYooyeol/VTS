package com.stock.db.controller;

import com.stock.db.domain.CorporationVO;
import com.stock.db.dto.Corporation.CorporationCriteria;
import com.stock.db.service.CorporationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CorporationController {
    private final CorporationService corporationService;

    @GetMapping(value = "corporations/search")
    public String searchCorporation(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "0") int sorting,
            @RequestParam(required = false, defaultValue = "-50") int min,
            @RequestParam(required = false, defaultValue = "50") int max,
            @RequestParam(required = false) String cname,
            Model model, Principal principal
    ){
        CorporationCriteria criteria = new CorporationCriteria(page, 10, sorting, cname, min, max);
        List<CorporationVO> corpList = corporationService.getPage(criteria);
        int maxPageNum = corporationService.getMaxPageNum(criteria);

        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
        }

        model.addAttribute("corp_list", corpList);
        model.addAttribute("criteria", criteria);
        model.addAttribute("max_page_num", maxPageNum);

        return "corporations/searchCorporation";
    }
}