package com.muzimin.jdbc_template.dao;

import com.muzimin.jdbc_template.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public void update(Book book) {
        Object[] args = {book.getUserId(), book.getUserName(), book.getUserStatus()};
        jdbcTemplate.update("update muzimin.t_book set user_id = ? where user_name = ? and user_status = ?", args);
    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update("delete from muzimin.t_book where user_id = ?", id);
    }

    @Override
    public int findCount() {
        return jdbcTemplate.queryForObject("select count(*) from muzimin.t_book", Integer.class);
    }

    @Override
    public Book findInfoById(String id) {
        return jdbcTemplate.queryForObject("select * from muzimin.t_book where user_id = ?", new BeanPropertyRowMapper<Book>(Book.class), id);
    }

    @Override
    public List<Book> findInfos() {
        return jdbcTemplate.query("select * from muzimin.t_book", new BeanPropertyRowMapper<Book>(Book.class));
    }

    @Override
    public void batchAdd(List<Object[]> args) {
        int[] ints = jdbcTemplate.batchUpdate("insert into muzimin.t_book value (?,?,?)", args);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchUpdate(ArrayList<Object[]> updateList) {
        int[] ints = jdbcTemplate.batchUpdate("update muzimin.t_book set user_name = ?,user_status = ? where user_id = ?", updateList);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchDelete(ArrayList<Object[]> deleteList) {
        int[] ints = jdbcTemplate.batchUpdate("delete from muzimin.t_book where user_id = ?", deleteList);
        System.out.println(Arrays.toString(ints));
    }


}
