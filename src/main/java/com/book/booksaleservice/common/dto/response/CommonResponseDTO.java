package com.book.booksaleservice.common.dto.response;

public class CommonResponseDTO extends ResponseDTO {

    private final Object data;

    public CommonResponseDTO(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }
}
