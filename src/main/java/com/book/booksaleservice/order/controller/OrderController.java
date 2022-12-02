package com.book.booksaleservice.order.controller;

import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.service.BookService;
import com.book.booksaleservice.common.SessionConst;
import com.book.booksaleservice.common.dto.response.CommonResponseDTO;
import com.book.booksaleservice.common.dto.response.ResponseDTO;
import com.book.booksaleservice.order.dto.OrderDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RequestMapping("/order")
@RestController
public class OrderController {

    private final BookService bookService;

    public OrderController(BookService bookService) {
        this.bookService = bookService;
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
}
