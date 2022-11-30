package com.book.booksaleservice.book.domain;

public class Category {
    private Long id;
    private final MainCategory mainCategory;
    private final SubCategory subCategory;

    public Category(MainCategory mainCategory, SubCategory subCategory) {
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }
}
