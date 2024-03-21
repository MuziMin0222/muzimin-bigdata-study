package com.muzimin.mapper;

import com.muzimin.pojo.Dept;
import org.apache.ibatis.annotations.Param;

/**
 * @author: 李煌民
 * @date: 2024-01-11 10:07
 **/
public interface DeptMapper {
    /**
     * 通过分步查询员工以及员工所对应的部门信息
     * 分步查询第二步：通过DID查询员工所在的部门
     *
     * @param did
     * @return
     */
    Dept selectById(@Param("did") int did);

    /**
     * 通过分步查询部门中的所有员工
     * 分步查询第一步：通过DID查询部门信息
     *
     * @param did
     * @return
     */
    Dept getDeptAndEmpsStepOne(@Param("did") Integer did);
}
