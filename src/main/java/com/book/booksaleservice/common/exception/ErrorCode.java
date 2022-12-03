package com.book.booksaleservice.common.exception;

public enum ErrorCode {
    BOOK_NOT_FOUND("존재하지 않는 도서입니다.", 404),
    NOT_ENOUGH_BOOK_AMOUNT("도서의 총 개수가 판매하려는 개수보다 적습니다.", 409);

    private final String message;
    private final int status;

    ErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
