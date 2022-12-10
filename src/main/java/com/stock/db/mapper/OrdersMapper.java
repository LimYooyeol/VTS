package com.stock.db.mapper;

import com.stock.db.domain.OrdersVO;
import com.stock.db.dto.Orders.MakeOrdersDto;
import com.stock.db.dto.Orders.OrdersDetailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper {

    /*
        @brief  : 주문 조회
        @return : 주문 정보 VO
        @param  :
            ono : 주문 번호
     */
    public OrdersVO findByOno(int Ono);

    /*
        @brief  : 주문
        @return : 업데이트 한 행의 개수
        @param  :
            ordersDto   : 주문 관련 DTO
     */
    public int makeOrders(MakeOrdersDto ordersDto);

    /*
        @brief  : 구매 체결
        @return : 업데이트 한 행의 개수
     */
    public int completeBuying(int ono);

    /*
        @brief  : 판매 체결
        @return : 업데이트 한 행의 개수
     */
    public int completeSale(int ono, double gain);

    /*
        @brief  : 대기 중인 주문 목록 조회
        @return : 대기 중인 주문 목록
     */
    public List<OrdersVO> getWaitOrders();

    public List<OrdersDetailDto> getOrdersByMno(int mno);

    public void cancelOrder(int ono);
}
