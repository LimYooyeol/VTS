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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.stock.db.service.MemberServiceTest.setTestMemberSignUpDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@EnableScheduling
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
    public void 구매_판매_동시_실패(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int price = priceMapper.getOpen(cno);
        long quantity = 10;

        MakeOrdersDto buyDto = createTestOrders(mno, cno, false, price, quantity);
        MakeOrdersDto saleDto = createTestOrders(mno, cno, true, price, quantity);

        // when
        int buyResult = ordersService.makeOrders(buyDto);
        int saleResult = ordersService.makeOrders(saleDto);

        // then
        assertNotEquals(-2, buyResult);
        assertEquals(-1, saleResult);
    }

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
        int price = priceMapper.getOpen(cno);
        long quantity = 10;
        MakeOrdersDto makeOrdersDto = createTestOrders(mno, cno, false, price, quantity);

        // when
        int ono = ordersService.makeOrders(makeOrdersDto);
        int updated = ordersService.checkWaitOrders();
        OrdersVO findOrders = ordersMapper.findByOno(ono);
        PossessesVO findPossesses = possessesMapper.findPossesses(mno, cno);
        MemberVO findMember = memberMapper.findByMno(mno);

        // then
        assertEquals(1, updated);
        assertNotNull(findPossesses);
        assertEquals(quantity, findPossesses.getQuantity());
        assertEquals(OrderState.COMPLETE, findOrders.getState());
        assertEquals(10000000 - price * quantity, findMember.getDeposit());

    }

    @Test
    public void 판매신청_실패(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int price = priceMapper.getOpen(cno) - 1000;
        long quantity = 10;
        MakeOrdersDto testOrders = createTestOrders(mno, cno, true, price, quantity);

        // when
        int result = ordersService.makeOrders(testOrders);

        // then
        assertEquals(-1, result);
    }

    @Test
    public void 판매신청_성공(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int buyPrice = priceMapper.getOpen(cno);
        long buyQuantity = 10;
        MakeOrdersDto buyOrdersDto = createTestOrders(mno, cno, false, buyPrice, buyQuantity);

        int ono = ordersService.makeOrders(buyOrdersDto);
        ordersService.checkWaitOrders();

        int salePrice = priceMapper.getOpen(cno) - 1000;
        long saleQuantity = 5;
        MakeOrdersDto saleOrdersDto = createTestOrders(mno, cno, true, salePrice, saleQuantity);

        // when
        int saleOno = ordersService.makeOrders(saleOrdersDto);

        // then
        assertNotEquals(-1, saleOno);
    }

    @Test
    public void 판매_채결(){
        // given
        int mno = createTestMember();
        String cno = "005930";
        int buyPrice = priceMapper.getOpen(cno);
        long buyQuantity = 10;
        MakeOrdersDto buyOrdersDto = createTestOrders(mno, cno, false, buyPrice, buyQuantity);

        ordersService.makeOrders(buyOrdersDto);
        ordersService.checkWaitOrders();

        int salePrice = priceMapper.getOpen(cno);
        long saleQuantity = 5;
        MakeOrdersDto saleOrdersDto = createTestOrders(mno, cno, true, salePrice, saleQuantity);

        // when
        int saleOno = ordersService.makeOrders(saleOrdersDto);
        int updated = ordersService.checkWaitOrders();
        OrdersVO findOrders = ordersMapper.findByOno(saleOno);
        PossessesVO findPossesses = possessesMapper.findPossesses(mno, cno);
        MemberVO member = memberMapper.findByMno(mno);

        // then
        assertEquals(OrderState.COMPLETE, findOrders.getState());
        assertEquals(1, updated);
        assertNotNull(findPossesses);
        assertEquals(buyQuantity - saleQuantity,findPossesses.getQuantity());
        assertEquals(10000000 + (salePrice*saleQuantity - buyPrice*buyQuantity), member.getDeposit());
    }

    /*
        @warning: '@Transactional' 붙이고 테스트하면 실패
                   --> 1. scheduled 메서드는 다른 thread 에서 동작
                   --> 2. 다른 thread 는 다른 transaction 이용
                   (테스트에서 write 하는 transaction 이 완료되지 않았으므로,
                   스케쥴링 thread 에서 허용 X)
     */
    @Test
    public void 매분마다_채결() throws InterruptedException {
        // given
        int mno = createTestMember();
        String cno = "005930";
        int price = priceMapper.getOpen(cno);
        long quantity = 10;
        MakeOrdersDto makeOrdersDto = createTestOrders(mno, cno, false, price, quantity);

        // when
        int ono = ordersService.makeOrders(makeOrdersDto);
        Thread.sleep(1000*70);
        OrdersVO findOrders = ordersMapper.findByOno(ono);
        PossessesVO findPossesses = possessesMapper.findPossesses(mno, cno);
        MemberVO member = memberMapper.findByMno(mno);

        // then
        assertEquals(OrderState.COMPLETE, findOrders.getState());
        assertNotNull(findPossesses);
        assertEquals(quantity, findPossesses.getQuantity());
        assertEquals(10000000 - price*quantity,member.getDeposit());
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