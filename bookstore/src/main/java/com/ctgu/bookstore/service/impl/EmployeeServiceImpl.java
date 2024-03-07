package com.ctgu.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.bookstore.entity.Employee;
import com.ctgu.bookstore.entity.User;
import com.ctgu.bookstore.mapper.EmployeeMapper;
import com.ctgu.bookstore.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee getByEmail(String email) {
        Employee employeeCondition = new Employee();
        employeeCondition.setEmail(email);
        QueryWrapper<Employee> qw = new QueryWrapper<>();
        qw.setEntity(employeeCondition);
        Employee employee = employeeMapper.selectOne(qw);
        return employee;
    }

    @Override
    public IPage<Employee> getAll(int page, int size) {
        IPage<Employee> employeeIPage = employeeMapper.selectPage(new Page<>(page, size), null);
        return employeeIPage;
    }

    @Override
    public IPage<Employee> getListUserByFuzzy(String field,int page, int size) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        if(field != null){
            wrapper.like("name",field).or()
                    .like("email",field).or()
                    .like("emp_id",field).or()
                    .like("department",field).or()
                    .like("sex",field).or()
                    .like("address",field).or()
                    .like("position",field).or()
                    .like("phone_number",field).or()
                    .like("salary",field);
        }
        IPage<Employee> employeeIPage = employeeMapper.selectPage(new Page<>(page, size), wrapper);
        return employeeIPage;
    }

    @Override
    public IPage<Employee> getAllByRequest(Employee query) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        if (query.getEmpId() != null) {
            wrapper.like("emp_id",query.getEmpId());
        }
        if(query.getPhoneNumber() != null) {
            wrapper.like("phone_number",query.getPhoneNumber());
        }
        if(query.getEmail() != null) {
            wrapper.like("email",query.getEmail());
        }
        if(query.getSalary() != null) {
            wrapper.like("salary",query.getSalary());
        }
        if(query.getDepartment() != null) {
            wrapper.like("department",query.getDepartment());
        }
        if(query.getPosition() != null) {
            wrapper.like("position",query.getPosition());
        }
        if(query.getName() != null) {
            wrapper.like("name",query.getName());
        }
        if(query.getSex() != null) {
            wrapper.like("sex",query.getSex());
        }
        if(query.getBirthday() != null) {
            wrapper.like("birthday",query.getBirthday());
        }
        if(query.getAddress() != null) {
            wrapper.like("adress",query.getAddress());
        }
        return employeeMapper.selectPage(new Page<>(1,10),wrapper);
    }


}
