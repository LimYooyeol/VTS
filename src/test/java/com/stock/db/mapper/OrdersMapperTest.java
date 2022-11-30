package com.stock.db.mapper;

import com.stock.db.domain.OrdersVO;
import com.stock.db.dto.Member.MemberSignUpDto;
import com.stock.db.dto.Orders.MakeOrdersDto;
import com.stock.db.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.stock.db.service.MemberServiceTest.setTestMemberSignUpDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrdersMapperTest {
    @Autowired OrdersMapper ordersMapper;
    @Autowired MemberService memberService;

    @Test
    @DisplayName("주문 생성 성공")
    public void 주문_테스트(){
        // given
        int mno = createTestMember();
        String cno = "005930";      // 삼성전자
        MakeOrdersDto makeOrdersDto = createTestOrder(mno, cno);

        // when
        ordersMapper.makeOrders(makeOrdersDto);
        int ono = makeOrdersDto.getOno();
        OrdersVO findOrders = ordersMapper.findByOno(ono);

        // then
        assertNotNull(findOrders);
        System.out.println(findOrders.toString());
    }
    public MakeOrdersDto createTestOrder(int mno, String cno){
        MakeOrdersDto makeOrdersDto = new MakeOrdersDto();

        makeOrdersDto.setMno(mno);
        makeOrdersDto.setCno(cno);
        makeOrdersDto.setIsSale(false);
        makeOrdersDto.setQuantity(10);
        makeOrdersDto.setPrice(10500);

        return makeOrdersDto;
    }

    public int createTestMember(){
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto();
        setTestMemberSignUpDto(memberSignUpDto);
        int mno = memberService.insertMember(memberSignUpDto);

        return mno;
    }

}