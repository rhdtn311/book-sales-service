package com.book.booksaleservice.book.controller;

import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.service.BookService;
import com.book.booksaleservice.common.dto.response.CommonResponseDTO;
import com.book.booksaleservice.common.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
