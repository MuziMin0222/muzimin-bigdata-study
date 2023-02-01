package com.muzimin.ioc.bean;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2023-01-28 16:05
 **/
public class Books {
    private List<String> bookList;

    public void setBookList(List<String> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookList=" + bookList +
                '}';
    }
}
