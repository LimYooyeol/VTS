package com.stock.db.service;

import com.stock.db.domain.MemberVO;
import com.stock.db.domain.OrderState;
import com.stock.db.domain.OrdersVO;
import com.stock.db.domain.PossessesVO;
import com.stock.db.dto.Member.MemberSignUpDto;
import com.stock.db.dto.Orders.MakeOrdersDto;
import com.stock.db.mapper.MemberMapper;
import com.stock.db.mapper.OrdersMapper;
import com.stock.db.mapper.PossessesMapper;
import com.stock.db.mapper.PriceMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.stock.db.service.MemberServiceTest.setTestMemberSignUpDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrdersServiceTest {

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired OrdersService ordersService;
    @Autowired MemberService memberService;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    PossessesMapper possessesMapper;

    @Autowired
    PriceMapper priceMapper;


    @Test
    public void 구매신청_성공(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int price = 60000;
        long quantity = 10;
        MakeOrdersDto makeOrdersDto = createTestOrders(mno, cno, false, price, quantity);

        // when
        int ono = ordersService.makeOrders(makeOrdersDto);
        OrdersVO findOrders = ordersMapper.findByOno(ono);
        MemberVO findMember = memberMapper.findByMno(mno);

        // then
        assertNotNull(findOrders);
        assertEquals(OrderState.WAIT, findOrders.getState());
        assertEquals(10000000 - price*quantity ,findMember.getDeposit());
    }

    @Test
    public void 구매신청_실패_예치금_초과(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int price = 60000;
        long quantity = 1000;
        MakeOrdersDto makeOrdersDto = createTestOrders(mno, cno, false, price, quantity);

        // when
        int ono = ordersService.makeOrders(makeOrdersDto);
        MemberVO findMember = memberMapper.findByMno(mno);

        // then
        assertEquals(-2, ono);
        assertEquals(10000000, findMember.getDeposit());
    }

    @Test
    public void 구매채결_테스트(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int price = 61500;
        long quantity = 10;
        MakeOrdersDto makeOrdersDto = createTestOrders(mno, cno, false, price, quantity);

        // when
        int ono = ordersService.makeOrders(makeOrdersDto);
        int updated = ordersService.checkWaitOrders();
        OrdersVO findOrders = ordersMapper.findByOno(ono);
        PossessesVO findPossesses = possessesMapper.findPossesses(mno, cno);
        MemberVO findMember = memberMapper.findByMno(mno);

        // then
        if(priceMapper.isPriceBetweenLowAndHigh(cno, price)) {
            assertEquals(1, updated);
            assertNotNull(findPossesses);
            assertEquals(quantity, findPossesses.getQuantity());
            assertEquals(OrderState.COMPLETE, findOrders.getState());
            assertEquals(10000000 - price * quantity, findMember.getDeposit());
        }
        else{
            assertEquals(0, updated);
            assertNull(findPossesses);
        }
    }


    public MakeOrdersDto createTestOrders(int mno, String cno, boolean isSale, int price, long quantity){
        MakeOrdersDto makeOrdersDto = new MakeOrdersDto();
        makeOrdersDto.setMno(mno);
        makeOrdersDto.setCno(cno);
        makeOrdersDto.setIsSale(isSale);
        makeOrdersDto.setPrice(price);
        makeOrdersDto.setQuantity(quantity);

        return makeOrdersDto;
    }
    public int createTestMember(){
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto();
        setTestMemberSignUpDto(memberSignUpDto);
        int mno = memberService.insertMember(memberSignUpDto);

        return mno;
    }

}