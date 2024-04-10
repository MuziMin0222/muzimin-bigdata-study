package com.muzimin.jdbc_template;

import com.muzimin.jdbc_template.entity.Book;
import com.muzimin.jdbc_template.service.BookService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 李煌民
 * @date: 2023-04-11 14:46
 **/
public class JdbcTemplateApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbc_template01.xml");
        BookService bookService = context.getBean("bookService", BookService.class);

        //add
        /*Book book = new Book();
        book.setUserId("1");
        book.setUserName("2");
        book.setUserStatus("3");
        bookService.addBook(book);*/

        //update
        /*Book book = new Book();
        book.setUserStatus("3");
        book.setUserName("2");
        book.setUserId("update");
        bookService.updateBook(book);*/

        //delete
        //bookService.deleteBook("update");

        //find count
        System.out.println(bookService.findCount());

        //find bookInfo
        System.out.println(bookService.findInfo("1"));

        //find booinfo list
        System.out.println(bookService.findInfoList());

        //batch insert data
        /*List<Object[]> insertList = new ArrayList<>();
        Object[] o1 = {"3","scala","90"};
        Object[] o2 = {"4","java","80"};
        Object[] o3 = {"5","Python","60"};
        insertList.add(o1);
        insertList.add(o2);
        insertList.add(o3);
        bookService.batchAdd(insertList);*/

        //批量更新
        /*ArrayList<Object[]> updateList = new ArrayList<>();
        Object[] o1 = {"scala_update", "90_", "3"};
        Object[] o2 = {"java_update", "80_", "4"};
        Object[] o3 = {"Python_update", "60_", "5"};
        updateList.add(o1);
        updateList.add(o2);
        updateList.add(o3);
        bookService.batchUpdate(updateList);*/


        //批量删除
        ArrayList<Object[]> deleteList = new ArrayList<>();
        Object[] o1 = {"3"};
        Object[] o2 = {"4"};
        Object[] o3 = {"5"};
        deleteList.add(o1);
        deleteList.add(o2);
        deleteList.add(o3);
        bookService.batchDelete(deleteList);
    }
}
