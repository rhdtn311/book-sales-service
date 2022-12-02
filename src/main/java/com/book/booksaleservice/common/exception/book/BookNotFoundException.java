package com.book.booksaleservice.common.exception.book;

import com.book.booksaleservice.common.exception.BusinessException;
import com.book.booksaleservice.common.exception.ErrorCode;

public class BookNotFoundException extends BusinessException {
    public BookNotFoundException() {
        super(ErrorCode.BOOK_NOT_FOUND);
    }
}
