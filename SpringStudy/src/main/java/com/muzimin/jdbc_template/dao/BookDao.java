package com.muzimin.jdbc_template.dao;

import com.muzimin.jdbc_template.entity.Book;

/**
 * @author: 李煌民
 * @date: 2023-03-09 12:02
 **/
public interface BookDao {
    void add(Book book);

    void update(Book book);

    void delete(String id);

    int findCount();
}
