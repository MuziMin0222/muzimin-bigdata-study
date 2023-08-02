package com.muzimin.dao;

import com.muzimin.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 李煌民
 * @date: 2023-08-02 17:06
 **/
@Repository
public class EmployeeDao {
    private static Map<Integer, Employee> employeeMap;

    static {
        employeeMap = new HashMap<>();
        employeeMap.put(1, new Employee(1, "aaa", "aa@qq.com", 1));
        employeeMap.put(2, new Employee(2, "bbb", "bb@qq.com", 0));
        employeeMap.put(3, new Employee(3, "ccc", "cc@qq.com", 1));
        employeeMap.put(4, new Employee(4, "ddd", "dd@qq.com", 0));
        employeeMap.put(5, new Employee(5, "eee", "ee@qq.com", 1));
    }

    private static Integer userId = 6;

    public void save(Employee employee) {
        if (employee != null && employee.getId() == null) {
            employee.setId(userId++);
        }

        assert employee != null;
        employeeMap.put(employee.getId(), employee);
    }

    public Collection<Employee> getAll() {
        return employeeMap.values();
    }

    public Employee get(Integer id) {
        return employeeMap.get(id);
    }

    public void delete(Integer id) {
        employeeMap.remove(id);
    }
}
