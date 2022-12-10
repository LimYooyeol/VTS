package com.stock.db.controller;

import com.stock.db.domain.CorporationVO;
import com.stock.db.domain.HoldersVO;
import com.stock.db.domain.PriceVO;
import com.stock.db.dto.Price.HistorySearchDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.stock.db.dto.Corporation.CorporationCriteria;
import com.stock.db.dto.Possesses.PossessesDetailDto;
import com.stock.db.service.CorporationService;
import com.stock.db.service.HoldersService;
import com.stock.db.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CorporationController {
    private final CorporationService corporationService;
    private final HoldersService holdersService;

    private final PriceService priceService;

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
            @RequestParam(defaultValue = "-1") int interval,
            Model model, Principal principal
    ){
        CorporationVO corp = corporationService.findByCno(cno);
        if(principal != null){
            model.addAttribute("user_id", principal.getName().toString());
        }

        model.addAttribute("corp", corp);
        model.addAttribute("interval", interval);

        return "stock/market";
    }

    @ResponseBody
    @GetMapping(value = "/corporations/history/{cno}")
    public String getHistory(
            @PathVariable String cno,
            @RequestParam(defaultValue = "-1") int interval
            ){
        HistorySearchDto historySearchDto = new HistorySearchDto();
        historySearchDto.setCno(cno);
        historySearchDto.setInterval(interval);

        List<PriceVO> priceHistory = priceService.getPriceHistory(historySearchDto);

        JSONObject response = new JSONObject();

        for(PriceVO p : priceHistory){
            JSONArray array = new JSONArray();
            array.add(p.getLow());
            array.add(p.getClose());
            array.add(p.getOpen());
            array.add(p.getHigh());
            response.put(p.getDate().toString(), array);
        }

        return response.toString();
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
        model.addAttribute("corporation", corp);
        model.addAttribute("holders", holders);

        return "corporation/corporation";
    }

}
