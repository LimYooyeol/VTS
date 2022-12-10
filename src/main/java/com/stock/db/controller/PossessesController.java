package com.stock.db.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.db.dto.Possesses.PossessesDetailDto;
import com.stock.db.mapper.PossessesMapper;
import com.stock.db.service.MemberService;
import com.stock.db.service.PossessesService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PossessesController {

    private final PossessesService possessesService;
    private final MemberService memberService;

    @ResponseBody
    @GetMapping(value = "/members/possession/ajax")
    private String possessionListAjax(Principal principal)
        throws IOException
    {
        String userId = principal.getName().toString();

        List<PossessesDetailDto> possesses = possessesService.findPossessesByUserId(userId);

        // 3. 다시 JSONObject를 생성한 후 전달할 값을 put
        JSONObject response = new JSONObject();
        for(PossessesDetailDto p : possesses){
            response.put(p.getCname(), p.getTotalPrice());
        }

        // 4. JSONOBject.toString() 을 이용하여 JSON 형태의 문자열로 response 전달
        return response.toString();
    }

    @GetMapping(value = "/members/possession")
    public String possessionList(
            Model model,
            Principal principal
    ){
        String userId = principal.getName().toString();

        long deposit = memberService.checkDeposit(userId);
        int mno = memberService.getMno(userId);

        List<PossessesDetailDto> possesses = possessesService.findPossessesByUserId(userId);

        model.addAttribute("user_id", principal.getName());
        model.addAttribute("possesses", possesses);
        model.addAttribute("deposit", deposit);

        return "mypage/possess";
    }
}
