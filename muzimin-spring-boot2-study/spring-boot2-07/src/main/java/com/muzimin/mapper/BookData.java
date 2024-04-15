package com.muzimin.mapper;

import com.muzimin.bean.Book;
import org.apache.ibatis.annotations.*;

/**
 * @author: 李煌民
 * @date: 2024-04-15 13:57
 **/
@Mapper
public interface BookData {
    @Select("select * from t_book where user_id = ${id}")
    Book getBook(@Param("id") Integer id);

    @Insert("insert into t_book(user_name,user_status) values(#{userName},#{userStatus})")
    @Options(useGeneratedKeys = true)
    void insertBook(Book book);
}
