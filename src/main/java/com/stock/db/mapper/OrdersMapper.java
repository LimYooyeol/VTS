package com.stock.db.mapper;

import com.stock.db.domain.OrdersVO;
import com.stock.db.dto.Orders.MakeOrdersDto;
import org.apache.ibatis.annotations.Mapper;

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

}
