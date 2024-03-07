package com.ctgu.bookstore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.bookstore.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
public interface EmployeeService extends IService<Employee> {

    Employee getByEmail(String email);
    IPage<Employee> getAll(int page, int size);

    IPage<Employee> getListUserByFuzzy(String field, int page, int size);

    IPage<Employee> getAllByRequest(Employee query);
}
