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

    @GetMapping("/{bookId}")
    public ResponseEntity<ResponseDTO> getSingleOrderPage(@PathVariable Long bookId) {
        BookDTO.Res book = bookService.findById(bookId);

        return ResponseEntity.ok(
                new CommonResponseDTO("조회 완료",
                        new OrderDTO.Res(List.of(book), book.price())
                ));
    }

    @PostMapping("/cart")
    public ResponseEntity<ResponseDTO> getCartOrderPage(@RequestBody List<BookDTO.Req> cart) {

        List<BookDTO.Res> books = bookService.findByAllIdAndChangeCount(cart);

        return ResponseEntity.ok(
                new CommonResponseDTO("조회 완료",
                        new OrderDTO.Res(books, OrderDTO.Res.totalPrice(books))
                ));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> order(@RequestBody OrderDTO.Req orderDTOReq,
                                             HttpServletRequest request) {
        Long orderId = orderService.save(orderDTOReq);

        request.getSession().removeAttribute(SessionConst.CART);

        return ResponseEntity.ok(
                new CommonResponseDTO("주문 완료",
                        orderId)
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getOrderList() {
        List<OrderDTO.OrderHistoryQueryRes> orderList = orderService.getOrderList();

        return ResponseEntity.ok(
                new CommonResponseDTO("조회 완료",
                        orderList)
        );
    }
}
