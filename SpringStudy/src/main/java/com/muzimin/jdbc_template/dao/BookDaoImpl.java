package com.muzimin.jdbc_template.dao;

import com.muzimin.jdbc_template.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author: 李煌民
 * @date: 2023-03-09 12:02
 **/
@Repository
public class BookDaoImpl implements BookDao {

    //注入JDBCTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Book book) {
        jdbcTemplate.update("insert into muzimin.t_book value (?,?,?)", book.getUserId(), book.getUserName(), book.getUserStatus());
    }
}
