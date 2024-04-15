package com.muzimin.service;

import com.muzimin.bean.CheckDataBean;
import com.muzimin.mapper.CheckData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2024-04-15 13:31
 **/
@Service
public class CheckDataService {
    @Autowired
    CheckData checkData;

    public List<CheckDataBean> getData(String etlDate) {
        return checkData.getDataByDate(etlDate);
    }
}
