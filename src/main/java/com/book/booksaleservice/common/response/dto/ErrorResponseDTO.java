package com.book.booksaleservice.common.response.dto;

import com.book.booksaleservice.common.exception.ErrorCode;

public class ErrorResponseDTO extends ResponseDTO {

    public ErrorResponseDTO(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
