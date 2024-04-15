package com.muzimin.mapper;

import com.muzimin.bean.CheckDataBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2024-04-15 13:24
 **/
@Mapper
public interface CheckData {
    List<CheckDataBean> getDataByDate(@Param("etlDate") String etlDate);
}
