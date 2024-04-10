package com.muzimin.ioc;

import com.muzimin.ioc.bean.Books;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2023-01-28 16:06
 **/
public class BookListDemo {
    @Test
    public void testBook() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bookList.xml");
        Books book = classPathXmlApplicationContext.getBean("book", Books.class);
        Books book1 = classPathXmlApplicationContext.getBean("book1", Books.class);

        System.out.println(book);
        System.out.println(book1);
    }
}
