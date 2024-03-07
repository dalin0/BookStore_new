package com.ctgu.bookstore.controller;


import com.ctgu.bookstore.entity.Employee;
import com.ctgu.bookstore.entity.Result;
import com.ctgu.bookstore.service.EmployeeService;
import com.ctgu.bookstore.utils.EmployExcelUtils;
import com.ctgu.bookstore.utils.ResultFactory;
import com.ctgu.bookstore.utils.UserExcelUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.bookstore.entity.Employee;
import com.ctgu.bookstore.entity.User;
import com.ctgu.bookstore.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

/**
 * @program: bookstore
 * @description:
 * @author: Nidol
 * @create: 2024-3-6
 **/
//解决跨域问题
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bookstore/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HttpSession session;

    @PostMapping(value = "/login/{email}/{password}")
    @ResponseBody
    public Result login(@RequestBody @PathVariable("email") String email,
                                     @PathVariable("password") String password,
                                     Model model) {
        Result result = new Result();
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email, password);
        System.out.println("封装后的token：" + usernamePasswordToken);
        System.out.println("试着取一下邮箱" + usernamePasswordToken.getUsername());
        // 执行登录方法
        try {
            subject.login(usernamePasswordToken);
            result.setCode(1);
            result.setMsg("登录成功");
            return result;
        } catch (UnknownAccountException e) {
            result.setCode(404);
            result.setMsg("邮箱错误");
            return result;
        } catch (IncorrectCredentialsException e) {
            result.setCode(403);
            result.setMsg("密码错误");
            return result;
        }
    }


    @GetMapping("/employeeLogin/{email}/{password}")
    @ApiOperation("职员通过邮箱密码登陆")
    public Result employeeLogin(@PathVariable("email")String email,
                                @PathVariable("password")String password, HttpServletRequest request){
        Result res = new Result();
        Employee employee = employeeService.getByEmail(email);
        if(employee == null){
            res.setCode(0);
            res.setMsg("用户不存在");
            return res;
        }

        if(!employee.getEmpPassword().equals(password)){
            res.setCode(0);
            res.setMsg("密码错误");
            return res;
        }
        res.setCode(1);
        res.setMsg("登陆成功");
        // 将email存入session中
        request.getSession().setAttribute("employees", email);
        res.setData(employee);
        session.setAttribute("employee",employee);
        res.setToken(UUID.randomUUID().toString());
        System.out.println(res);
        return res;
    }

    @GetMapping("/employeeLogout")
    @ApiOperation("实现职员的退出")
    public Result employeeLogout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        session.invalidate();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping("/export")
    @ApiOperation("批量导出雇员信息")
    public ResponseEntity<byte[]> exportUser(){
        List list = employeeService.list(null);
        return EmployExcelUtils.export(list);
    }

    @GetMapping("/find/{id}")
    @ApiOperation("根据id查找职员")
    public Employee getById(@PathVariable("id") int id){
        return employeeService.getById(id);
    }

    @GetMapping("/findAll/{page}/{size}")
    @ApiOperation("查找所有用户，实现分页")
    public IPage<Employee> getAll(@PathVariable("page") int page, @PathVariable("size") int size){
        return employeeService.getAll(page,size);
    }

    @GetMapping("/findList/{field}/{page}/{size}")
    @ApiOperation("字段模糊查询")
    public IPage<Employee> getListEmployeeByFuzzy(@PathVariable("field") String field, @PathVariable("page") int page, @PathVariable("size") int size){
        return employeeService.getListUserByFuzzy(field,page,size);
    }

    @PostMapping("/findByRequest")
    @ApiOperation("条件查询，实现分页，默认从第一页显示，每页显示10条记录")
    public IPage<Employee> getAllByRequest(@RequestBody Employee query){
        return employeeService.getAllByRequest(query);
    }

    @PostMapping("/add")
    @ApiOperation("添加职员")
    public Result addEmployee(@RequestBody Employee employee){
        employeeService.save(employee);
        Result result = new Result();
        result.setCode(200);
        result.setMsg("添加职员成功");
        return result;
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation("删除职员")
    public Result delEmployee(@PathVariable("id") int id) {
        employeeService.removeById(id);
        Result result = new Result();
        result.setCode(200);
        return result;
    }

    @PostMapping("/edit")
    @ApiOperation("更新职员")
    public Result editEmployee(@RequestBody Employee employee, HttpServletRequest httpServletRequest) {
        employeeService.updateById(employee);
        Result result = new Result();
        result.setCode(200);
        return result;
    }

//    @PostMapping("/saveToken/{id}/{token}")
//    @ApiOperation("将临时凭证存入")
//    public void saveToken(@PathVariable("id") int id, @PathVariable("token") String token){
//        Employee employee = employeeService.getById(id);
//        employee.setToken(token);
//        employeeService.updateById(employee);
//        System.out.println("获得临时凭证"+token);
//    }
    @GetMapping("/getEmpInfo")
    @ApiOperation("得到当前登录用户的信息")
    public Employee getEmpInfo(HttpServletRequest request){
        String email = (String) request.getSession().getAttribute("employees");
        System.out.println("获取当前登录用户的信息啊：" + email);
        return employeeService.getByEmail(email);
    }


}
