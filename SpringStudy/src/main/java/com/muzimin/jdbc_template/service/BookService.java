package com.muzimin.jdbc_template.service;

import com.muzimin.jdbc_template.dao.BookDao;
import com.muzimin.jdbc_template.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 李煌民
 * @date: 2023-03-09 12:03
 **/

@Service
public class BookService {

    //注入DAO
    @Autowired
    private BookDao bookDao;

    public void addBook(Book book){
        bookDao.add(book);
    }
}
