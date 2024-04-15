package com.muzimin.controller;

import com.muzimin.bean.Book;
import com.muzimin.bean.CheckDataBean;
import com.muzimin.mapper.BookData;
import com.muzimin.service.BookDataService;
import com.muzimin.service.CheckDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2024-04-15 13:29
 **/
@RestController
public class MyBatisSourceController {

    @Autowired
    CheckDataService checkDataService;
    @Autowired
    BookDataService bookDataService;

    @GetMapping("/check")
    List<CheckDataBean> getData(@Param("etlDate") String etlDate) {
        return checkDataService.getData(etlDate);
    }

    @GetMapping("/book")
    Book getBookData(@Param("userId") Integer userId) {
        return bookDataService.getBookData(userId);
    }

    @PostMapping("/saveBook")
    Book saveBook(@Param("book") Book book) {
        bookDataService.saveBook(book);
        return book;
    }
}
