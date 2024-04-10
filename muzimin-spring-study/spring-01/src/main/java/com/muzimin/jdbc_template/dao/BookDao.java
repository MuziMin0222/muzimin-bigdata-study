package com.muzimin.jdbc_template.dao;

import com.muzimin.jdbc_template.entity.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 李煌民
 * @date: 2023-03-09 12:02
 **/
public interface BookDao {
    void add(Book book);

    void update(Book book);

    void delete(String id);

    int findCount();

    Book findInfoById(String id);

    List<Book> findInfos();

    void batchAdd(List<Object[]> args);

    void batchUpdate(ArrayList<Object[]> updateList);

    void batchDelete(ArrayList<Object[]> deleteList);
}
