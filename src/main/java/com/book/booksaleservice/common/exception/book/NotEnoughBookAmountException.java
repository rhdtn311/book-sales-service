package com.book.booksaleservice.common.exception.book;

import com.book.booksaleservice.common.exception.BusinessException;
import com.book.booksaleservice.common.exception.ErrorCode;

public class NotEnoughBookAmountException extends BusinessException {
    public NotEnoughBookAmountException() {
        super(ErrorCode.NOT_ENOUGH_BOOK_AMOUNT);
    }
}
