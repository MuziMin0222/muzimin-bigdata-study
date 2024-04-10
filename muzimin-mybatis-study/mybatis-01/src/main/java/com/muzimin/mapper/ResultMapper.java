package com.muzimin.mapper;

import com.muzimin.pojo.Dept;
import com.muzimin.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2024-01-10 09:42
 **/
public interface ResultMapper {
    List<Emp> selectEmpById(@Param("eid") int eid);

    /**
     * 获取部门以及部门中所有的员工信息
     * @return
     */
    Dept getDeptAndEmp(@Param("did") Integer did);
}
