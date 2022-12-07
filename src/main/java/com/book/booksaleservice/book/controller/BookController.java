package com.book.booksaleservice.book.controller;

import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.service.BookService;
import com.book.booksaleservice.common.SessionConst;
import com.book.booksaleservice.common.dto.response.CommonResponseDTO;
import com.book.booksaleservice.common.dto.response.ResponseDTO;
import com.book.booksaleservice.common.exception.book.BookNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PostMapping("/cart/{id}")
    public ResponseEntity<ResponseDTO> putBookInCart(@PathVariable Long id,
                                                     HttpServletRequest request) {

        if (!bookService.existBook(id)) {
            throw new BookNotFoundException();
        }

        HttpSession session = request.getSession();
        if (session.getAttribute(SessionConst.CART) == null) {
            session.setAttribute(SessionConst.CART, new HashSet<>(Set.of(id)));
        } else {
            ((HashSet<Long>) session.getAttribute(SessionConst.CART)).add(id);
        }

        return ResponseEntity.ok(
                new CommonResponseDTO("장바구니에 아이템 담기 완료", id)
        );
    }

    @GetMapping("/cart")
    public ResponseEntity<ResponseDTO> getCart(HttpServletRequest request) {
        List<BookDTO.Res> books = new ArrayList<>();

        HttpSession session = request.getSession();

        if (!isEmptyCart(session)) {
            HashSet<Long> ids = (HashSet<Long>) session.getAttribute(SessionConst.CART);
            books = bookService.findByAllId(new ArrayList<>(ids));
        }

        return ResponseEntity.ok(
                new CommonResponseDTO("조회 완료", books)
        );
    }

    private boolean isEmptyCart(HttpSession session) {
        return session.getAttribute(SessionConst.CART) == null || ((HashSet<Long>) session.getAttribute(SessionConst.CART)).isEmpty();
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<ResponseDTO> deleteBookAtCart(HttpServletRequest request,
                                                        @PathVariable Long id) {
        HttpSession session = request.getSession();
        boolean deleteResult = false;
        if (!isEmptyCart(session)) {
            HashSet<Long> ids = (HashSet<Long>) session.getAttribute(SessionConst.CART);

            deleteResult = deleteId(ids, id);
        }


        return ResponseEntity.ok(
                new CommonResponseDTO(deleteResult ? "삭제 완료" : "삭제된 데이터 없음", deleteResult)
        );
    }

    private boolean deleteId(HashSet<Long> ids, Long id) {
        return ids.remove(id);
    }
}
