package com.book.booksaleservice.book.controller;

import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.book.booksaleservice.ApiDocumentUtils.getDocumentRequest;
import static com.book.booksaleservice.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost")
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("책 목록 조회 요청 성공")
    void findBooksByCategory() throws Exception {

        // given
        String mainCategoryParam = "DOMESTIC";
        String subCategoryParam = "NOVEL";

        List<BookDTO.Res> response = List.of(
                new BookDTO.Res(1L, "종의 기원", "정유정", "은행나무", "줄거리", 13000)
        );

        when(bookService.findByCategory(mainCategoryParam, subCategoryParam))
                .thenReturn(response);

        // when
        ResultActions result = this.mockMvc.perform(
                get("http://localhost:8080/book/{mainCategory}/{subCategory}", mainCategoryParam, subCategoryParam)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("find-books-by-category",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("mainCategory").description("메인 카테고리"),
                                parameterWithName("subCategory").description("서브 카테고리")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("결과 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
                                fieldWithPath("data[0].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data[0].author").type(JsonFieldType.STRING).description("작가"),
                                fieldWithPath("data[0].publisher").type(JsonFieldType.STRING).description("출판사"),
                                fieldWithPath("data[0].price").type(JsonFieldType.NUMBER).description("가격")
                        )));
    }

    @Test
    @DisplayName("책 상세 조회 완료")
    void findBookById() throws Exception {
        // given
        Long id = 1L;

        BookDTO.Res response = new BookDTO.Res(1L, "title", "author", "publisher", "plot", 15000);

        when(bookService.findById(id))
                .thenReturn(response);

        // when
        ResultActions result = this.mockMvc.perform(
                get("http://localhost:8080/book/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("find-book-by-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("도서 ID")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("결과 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("data.publisher").type(JsonFieldType.STRING).description("출판사"),
                                fieldWithPath("data.author").type(JsonFieldType.STRING).description("작가"),
                                fieldWithPath("data.plot").type(JsonFieldType.STRING).description("줄거리")
                        )));
    }
}