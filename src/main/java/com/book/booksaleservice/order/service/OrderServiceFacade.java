package com.book.booksaleservice.order.service;

import com.book.booksaleservice.common.exception.common.UpdateFailException;
import com.book.booksaleservice.order.dto.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFacade {

    private final OrderService orderService;

    public OrderServiceFacade(OrderService orderService) {
        this.orderService = orderService;
    }

    public Long save(OrderDTO.Req orderDtoReq) {

        for (int tryTime = 1; tryTime <= 40; tryTime++) {
            try {
                return orderService.save(orderDtoReq);
            } catch (UpdateFailException e) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        throw new IllegalArgumentException("잠시 후 다시 시도해주세요.");
    }
}
