package com.book.booksaleservice.common.response.dto;

public class ResponseDTO {
    private int status;
    private String message;

    public ResponseDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
