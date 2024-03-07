package com.ctgu.bookstore.mapper;

import com.ctgu.bookstore.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    String getPassword(String email);

    String getRole(String email);
}
