package com.muzimin.service;

import com.muzimin.bean.Book;
import com.muzimin.mapper.BookData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 李煌民
 * @date: 2024-04-15 13:56
 **/
@Service
public class BookDataService {

    @Autowired
    BookData bookData;

    public Book getBookData(Integer userId) {
        return bookData.getBook(userId);
    }

    public void saveBook(Book book) {
        bookData.insertBook(book);
    }
}
