package com.book.booksaleservice.common.dto.response;

public class CommonResponseDTO extends ResponseDTO {

    private final Object data;

    public CommonResponseDTO(String message, Object data) {
        super(200, message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
