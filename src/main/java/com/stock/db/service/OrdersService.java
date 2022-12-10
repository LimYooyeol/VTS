package com.stock.db.service;

import com.stock.db.common.TradeTime;
import com.stock.db.domain.MemberVO;
import com.stock.db.domain.OrdersVO;
import com.stock.db.domain.PossessesVO;
import com.stock.db.dto.Orders.MakeOrdersDto;
import com.stock.db.dto.Orders.OrdersDetailDto;
import com.stock.db.dto.Possesses.PossessesDto;
import com.stock.db.mapper.MemberMapper;
import com.stock.db.mapper.OrdersMapper;
import com.stock.db.mapper.PossessesMapper;
import com.stock.db.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersMapper ordersMapper;

    private final PriceMapper priceMapper;

    private final PossessesMapper possessesMapper;

    private final MemberMapper memberMapper;

    public List<OrdersDetailDto> getOrdersByMno(int mno){
        return ordersMapper.getOrdersByMno(mno);
    }

    /*
        @brief  : 주문 로직
        @detail :
            주문만 처리하고, 체결은 별도 처리.
            체결은 장 시간 중에만 이뤄짐. (common.TradeTime.open ~ common.TradeTime.close)
            장 시간 외의 주문은 예약과 동일한 기능.
        @return :
            -1     : 보유 수량 이상 판매
            -2     : 보유 금액 이상 구매
            주문번호 : 거래 성공
        @param  :
            makeOrdersDto : 주문 관련 정보
     */
    public int makeOrders(MakeOrdersDto makeOrdersDto){
        int nowHour = LocalDateTime.now().getHour();
        MemberVO orderMember = memberMapper.findByMno(makeOrdersDto.getMno());
        PossessesVO possessesVO = possessesMapper.findPossesses(makeOrdersDto.getMno(), makeOrdersDto.getCno());
        long possessesQuantity = (possessesVO == null) ? 0 : possessesVO.getQuantity();

        if(makeOrdersDto.getIsSale() && (possessesQuantity < makeOrdersDto.getQuantity())){
            // 보유 수량 이상 판매
            return -1;
        }
        else if(!makeOrdersDto.getIsSale() && orderMember.getDeposit() < makeOrdersDto.getQuantity() * makeOrdersDto.getPrice()){
            // 보유 금액 이상 구매
            return -2;
        }

        ordersMapper.makeOrders(makeOrdersDto);
        if(!makeOrdersDto.getIsSale()){
            long volume = makeOrdersDto.getQuantity() * makeOrdersDto.getPrice();
            memberMapper.updateDeposit(makeOrdersDto.getMno(), -volume);
        }

        int ono = makeOrdersDto.getOno();

        return ono;
    }

    /*
        @brief  : 구매 체결
        @detail :
            possesses 에 거래 반영
            orders.trade_time, orders.state 업데이트
        @return : 체결 한 주문 번호
        @param  :
            ordersVO    : 체결 할 주문
     */
    public int completeBuying(OrdersVO ordersVO){
        PossessesVO prevPossesses = possessesMapper.findPossesses(ordersVO.getMno(), ordersVO.getCno());

        PossessesDto newPossesses = new PossessesDto();
        newPossesses.setMno(ordersVO.getMno());
        newPossesses.setCno(ordersVO.getCno());

        double prevTotalPrice = (prevPossesses == null) ? 0 : prevPossesses.getAvgPrice()* prevPossesses.getQuantity();
        double newTotalPrice = prevTotalPrice + ordersVO.getPrice()*ordersVO.getQuantity();
        long newQuantity = (prevPossesses == null ? 0 : prevPossesses.getQuantity())+ ordersVO.getQuantity();
        double newAvgPrice = newTotalPrice / newQuantity;

        newPossesses.setAvgPrice(newAvgPrice);
        newPossesses.setQuantity(newQuantity);

        possessesMapper.reflectPossesses(newPossesses);
        ordersMapper.completeBuying(ordersVO.getOno());

        return ordersVO.getOno();
    }

    /*
        @brief  : 판매 체결
        @detail :
            possesses 에 거래 반영
            orders.trade_time, orders.state, orders.gain 업데이트
            member.deposit 업데이트
        @return : 체결 한 주문 번호
        @param  :
            ordersVO    : 체결 할 주문
     */
    public int completeSale(OrdersVO ordersVO){

        PossessesVO findPossesses = possessesMapper.findPossesses(ordersVO.getMno(), ordersVO.getCno());

        PossessesDto newPossesses = new PossessesDto();
        newPossesses.setMno(ordersVO.getMno());
        newPossesses.setCno(ordersVO.getCno());
        newPossesses.setQuantity(findPossesses.getQuantity() - ordersVO.getQuantity());
        newPossesses.setAvgPrice(findPossesses.getAvgPrice());

        double gain = -(findPossesses.getAvgPrice() - ordersVO.getPrice())*ordersVO.getQuantity();


        possessesMapper.reflectPossesses(newPossesses);
        ordersMapper.completeSale(ordersVO.getOno(), gain);
        memberMapper.updateDeposit(ordersVO.getMno(), ordersVO.getPrice()*ordersVO.getQuantity());

        return ordersVO.getOno();
    }


    /*
        @brief  : 대기 중인 주문 확인 및 체결
        @detail :
            #fix : 장 중임을 가정
            대기 중인 주문을 확인하여 저가와 고가 사이의 가격이면 체결
        @return : 체결된 주문의 수
     */
    public int checkWaitOrders(){
        List<OrdersVO> waitOrders = ordersMapper.getWaitOrders();
        int updated = 0;
        for(OrdersVO orders : waitOrders){
            if(!priceMapper.isPriceBetweenLowAndHigh(orders.getCno(), orders.getPrice())){
                continue;
            }

            if(orders.isSale()){
                completeSale(orders);
            }
            else{
                completeBuying(orders);
            }
            updated ++;
        }

        return updated;
    }

    public void cancelOrder(int ono) {
        ordersMapper.cancelOrder(ono);
    }
}
