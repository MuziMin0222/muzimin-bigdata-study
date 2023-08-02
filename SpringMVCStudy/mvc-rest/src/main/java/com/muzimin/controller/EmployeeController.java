package com.muzimin.controller;

import com.muzimin.bean.Employee;
import com.muzimin.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * @author: 李煌民
 * @date: 2023-08-02 17:09
 **/
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getAll(Model model) {
        Collection<Employee> employeeDaoAll = employeeDao.getAll();

        model.addAttribute("employeeList", employeeDaoAll);

        return "employee";
    }
}
