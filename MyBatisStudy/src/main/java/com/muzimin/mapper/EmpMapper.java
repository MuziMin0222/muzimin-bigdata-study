package com.muzimin.mapper;

import com.muzimin.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2024-01-11 10:07
 **/
public interface EmpMapper {
    Emp selectByEid(@Param("eid") int eid);

    /**
     * 通过分步查询部门中的所有员工
     * 分步查询第二步：通过DID查询员工信息
     * @param did
     * @return
     */
    List<Emp> getDeptAndEmpStepTwo(@Param("did") Integer did);
}
