package com.muzimin.jdbc_template.service;

import com.muzimin.jdbc_template.dao.BookDao;
import com.muzimin.jdbc_template.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 李煌民
 * @date: 2023-03-09 12:03
 **/

@Service
public class BookService {

    //注入DAO
    @Autowired
    private BookDao bookDao;

    public void addBook(Book book) {
        bookDao.add(book);
    }

    public void updateBook(Book book) {
        bookDao.update(book);
    }

    public void deleteBook(String id) {
        bookDao.delete(id);
    }

    public int findCount() {
        return bookDao.findCount();
    }

    public Book findInfo(String id) {
        return bookDao.findInfoById(id);
    }

    public List<Book> findInfoList() {
        return bookDao.findInfos();
    }

    public void batchAdd(List<Object[]> args) {
        bookDao.batchAdd(args);
    }

    public void batchUpdate(ArrayList<Object[]> updateList) {
        bookDao.batchUpdate(updateList);
    }

    public void batchDelete(ArrayList<Object[]> deleteList) {
        bookDao.batchDelete(deleteList);
    }
}
