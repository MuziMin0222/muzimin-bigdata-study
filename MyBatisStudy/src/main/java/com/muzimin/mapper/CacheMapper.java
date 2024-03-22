package com.muzimin.mapper;

import com.muzimin.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2024-03-22 15:05
 **/
public interface CacheMapper {
    List<Emp> getEmp(@Param("eid") Integer eid);
}
