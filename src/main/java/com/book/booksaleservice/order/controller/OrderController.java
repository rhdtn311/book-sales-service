package com.book.booksaleservice.order.controller;

import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.service.BookService;
import com.book.booksaleservice.common.SessionConst;
import com.book.booksaleservice.common.dto.response.CommonResponseDTO;
import com.book.booksaleservice.common.dto.response.ResponseDTO;
import com.book.booksaleservice.order.dto.OrderDTO;
import com.book.booksaleservice.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RequestMapping("/order")
@RestController
public class OrderController {

    private final BookService bookService;
    private final OrderService orderService;

    public OrderController(BookService bookService, OrderService orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getOrderPage(HttpServletRequest request) {
        HashSet<Long> cart = (HashSet<Long>) request.getSession().getAttribute(SessionConst.CART);

        List<BookDTO.Res> books = bookService.findByAllId(cart);

        return ResponseEntity.ok(
                new CommonResponseDTO("조회 완료",
                        new OrderDTO.Res(books, OrderDTO.Res.totalPrice(books))
                ));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> order(@RequestBody OrderDTO.Req orderDTOReq) {
        Long orderId = orderService.save(orderDTOReq);

        return ResponseEntity.ok(
                new CommonResponseDTO("주문 완료",
                        orderId)
        );
    }
}
