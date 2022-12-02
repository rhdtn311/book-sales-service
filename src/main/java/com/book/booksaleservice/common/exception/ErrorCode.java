package com.book.booksaleservice.common.exception;

public enum ErrorCode {
    BOOK_NOT_FOUND("존재하지 않는 도서입니다.", 404);

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
