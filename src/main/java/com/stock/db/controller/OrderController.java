package com.stock.db.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.stock.db.dto.Orders.MakeOrdersDto;
import com.stock.db.service.MemberService;
import com.stock.db.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;
    private final MemberService memberService;

    @ResponseBody
    @PostMapping(value = "/orders/buying")
    public String buyStock(
            HttpServletRequest request, Principal principal
    ) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        inputStream.close();

        Map<String, Object> json = new ObjectMapper().readValue(messageBody, HashMap.class);


        String cno = (String) json.get("cno");
        int mno = memberService.getMno(principal.getName().toString());
        Boolean isSale = (Boolean) json.get("isSale");
        long quantity = Long.parseLong(json.get("quantity").toString());
        int price = Integer.parseInt(json.get("price").toString());

        MakeOrdersDto makeOrdersDto = new MakeOrdersDto(
                mno,
                cno,
                isSale,
                quantity,
                price
        );

        int result = ordersService.makeOrders(makeOrdersDto);

        JSONObject response = new JSONObject();
        response.put("result", result);

        return response.toString();
    }
}
