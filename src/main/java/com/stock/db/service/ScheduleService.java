package com.stock.db.service;

import com.stock.db.mapper.OrdersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final OrdersService ordersService;

    private final OrdersMapper ordersMapper;

    @Scheduled(cron = "00 * * * * *")
    @Transactional
    public void scheduleTest(){
//        System.out.println("============");
//        System.out.println(LocalDateTime.now());
//        System.out.println(ordersMapper.getWaitOrders().size());
//        System.out.println(ordersService.checkWaitOrders());
//        System.out.println(Thread.currentThread().getId());
    }

}
