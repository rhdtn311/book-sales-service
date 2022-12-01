package com.book.booksaleservice.common.response.dto;

public class CommonResponseDTO extends ResponseDTO {

    private final Object data;

    public CommonResponseDTO(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }
}
