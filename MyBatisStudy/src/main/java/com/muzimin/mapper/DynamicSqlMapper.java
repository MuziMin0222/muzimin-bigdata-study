package com.muzimin.mapper;

import com.muzimin.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2024-03-21 17:25
 **/
public interface DynamicSqlMapper {
    List<Emp> getEmps(Emp emp);

    List<Emp> getEmpsByList(@Param("ids") Integer[] ids);

}
