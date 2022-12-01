package com.book.booksaleservice.common.dto.response;

public class ResponseDTO {
    private final int status;
    private final String message;

    public ResponseDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
