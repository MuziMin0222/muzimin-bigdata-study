package com.muzimin.transfer_spring.xml_file.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author: 李煌民
 * @date: 2023-07-03 13:44
 **/
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void addMoney() {
        jdbcTemplate.update("update t_money set money = money + ? where name = ?", 100, "luzhen");
    }

    @Override
    public void reduceMoney() {
        jdbcTemplate.update("update t_money set money = money - ? where name = ?", 100, "muzimin");
    }
}
