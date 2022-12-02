package com.book.booksaleservice.book.controller;

import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.service.BookService;
import com.book.booksaleservice.common.SessionConst;
import com.book.booksaleservice.common.dto.response.CommonResponseDTO;
import com.book.booksaleservice.common.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/book")
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{mainCategory}/{subCategory}")
    public ResponseEntity<ResponseDTO> getBookList(@PathVariable(value = "mainCategory") String mainCategory,
                                                   @PathVariable(value = "subCategory") String subCategory) {
        List<BookDTO.Res> books = bookService.findByCategory(mainCategory, subCategory);

        return ResponseEntity.ok(
                new CommonResponseDTO("조회 완료", books)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getBook(@PathVariable Long id) {
        BookDTO.Res book = bookService.findById(id);
        return ResponseEntity.ok(
                new CommonResponseDTO("조회 완료", book)
        );
    }

    @PostMapping("/cart")
    public ResponseEntity<ResponseDTO> putBookInCart(@RequestParam(value = "id") Long id,
                                                     HttpServletRequest request) {

        bookService.existBook(id);

        HttpSession session = request.getSession();
        if (session.getAttribute(SessionConst.CART) == null) {
            session.setAttribute(SessionConst.CART, new HashSet<>(Set.of(id)));
        } else {
            ((HashSet<Long>) session.getAttribute(SessionConst.CART)).add(id);
        }

        return ResponseEntity.ok(
                new CommonResponseDTO("장바구니에 아이템 담기 성공", null)
        );
    }
}
