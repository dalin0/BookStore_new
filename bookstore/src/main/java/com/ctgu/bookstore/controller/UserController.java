package com.ctgu.bookstore.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.bookstore.entity.Book;
import com.ctgu.bookstore.entity.Dto.UserDto;
import com.ctgu.bookstore.entity.User;
import com.ctgu.bookstore.entity.VerifyCode;
import com.ctgu.bookstore.service.UserService;
import com.ctgu.bookstore.utils.*;
import com.ctgu.bookstore.utils.authCode.IVerifyCodeGen;
import com.ctgu.bookstore.utils.authCode.SimpleCharVerifyCodeGenImpl;
import com.ctgu.bookstore.utils.vo.ResultEnum;
import io.swagger.annotations.ApiOperation;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ctgu.bookstore.entity.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: bookstore
 * @description:
 * @author: Nidol
 * @create: 2024-3-6
 **/
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/bookstore/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private  HttpSession session;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    JwtUtils jwtUtils;

//    @GetMapping("/userLogin/{email}/{password}")
//    @ApiOperation("用于测试登录")
//    public Result userLogin(@PathVariable("email")String email,
//                            @PathVariable("password")String password){
//        Result res = new Result();
//        User user = userService.getByEmail(email);
//        if(user == null){
//            res.setCode(0);
//            res.setMsg("用户不存在");
//            return res;
//        }
//        if(!user.getUserPassword().equals(password)){
//            res.setCode(0);
//            res.setMsg("密码错误");
//            return res;
//        }
//        res.setCode(1);
//        res.setMsg("登陆成功");
//        res.setData(user);
//        session.setAttribute("user", user);
//        String token = UUID.randomUUID().toString();
//        res.setToken(token);
//        return res;
//    }
    @GetMapping("/total/")
    @ApiOperation("获得用户总数")
    public int getTotalUser(){
        return userService.list(null).size();
    }


    @PostMapping(value = "/userLogin")
    @ApiOperation("会员登录")
    public com.ctgu.bookstore.utils.vo.Result login(@RequestBody UserDto userDto) {
        log.info("登录信息： " + userDto);
        User user = null;
        try {
            user = userService.getByEmail(userDto.getEmail());
        } catch (Exception e) {
            return ResultUtil.error("网络有误，请刷新后重试");
        }
        log.info("用户信息： " + user);
        if (user == null) {
            return ResultUtil.error(ResultEnum.LOGIN_FAILED);
        }
        if (!EncryptionUtils.getSaltverifyMD5(userDto.getUserPassword(), user.getUserPassword(), user.getSalt())){
            return ResultUtil.error(ResultEnum.LOGIN_FAILED);
        }
        // 从Redis中取出验证码
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        if (!operations.get("LoginCode").equals(userDto.getVerCode())) {
            return ResultUtil.error("验证码错误", ResultEnum.FAIL);
        }
        // 验证通过， 生成token， 并返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("avatar", user.getAvatar());
        // 封装用户数据
        String token = jwtUtils.createJwt(user.getUserId().toString(), userDto.getEmail(), map);
        log.info("生成的token串： " + token);
        return ResultUtil.success(token, ResultEnum.SUCCESS, user);
    }


    @PostMapping("/register")
    @ApiOperation("会员注册")
    public com.ctgu.bookstore.utils.vo.Result register(@RequestBody User user){
        String passWord = user.getUserPassword();
        System.out.println(passWord);
        log.info("用户信息第一个： " + user);
        User newUser = new User();
        try{
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("email", user.getEmail());
            newUser = userService.getOne(qw);
        } catch (Exception e) {
            return ResultUtil.error("网络异常，请刷新后重试");
        }
        if (newUser != null) {
            return ResultUtil.error("邮箱已存在");
        }
        try {
            // 随机生成盐
            String salt = UUID.randomUUID().toString();
            // 存储加密后的密码
            user.setUserPassword(EncryptionUtils.getSaltMD5(user.getUserPassword(), salt));
            user.setSalt(salt);
            user.setAvatar("http://s9vf5jis6.hn-bkt.clouddn.com/img/1.jpg");
            userService.save(user);
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResultUtil.error("注册信息有误");
        }
//        return ResultUtil.success("注册成功");
        // 注册完成，进行登录操作
        User user1 = null;
        try {
            user1 = userService.getByEmail(user.getEmail());
        } catch (Exception e) {
            return ResultUtil.error("网络有误，请刷新后重试");
        }
        log.info("用户信息user1： " + user1);
        if (user1 == null) {
            return ResultUtil.error(ResultEnum.LOGIN_FAILED);
        }
        if (!EncryptionUtils.getSaltverifyMD5(passWord, user1.getUserPassword(), user1.getSalt())) {
            return ResultUtil.error(ResultEnum.LOGIN_FAILED);
        }
        // 验证通过， 生成token， 并返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("avatar", user1.getAvatar());
        String token = jwtUtils.createJwt(user1.getUserId().toString(), user1.getEmail(), map);
        log.info("生成的token串： " + token);
        return ResultUtil.success(token, ResultEnum.SUCCESS, user1);
    }

//    @PostMapping("/register/{email}/{password}")
//    @ApiOperation("用于测试功能是否正常")
//    public Result testTegister(@RequestBody @PathVariable("email") String email, @PathVariable("password") String password){
//        User user = new User();
//        email = HtmlUtils.htmlEscape(email);
//        user.setEmail(email);
//        boolean exist = userService.isExist(email);
//        if (!exist) {
//            String message = "用户名已被使用";
//            return ResultFactory.buildFailResult(message);
//        }
//        // 生成盐，默认长度为16位
//        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
//        // 设置 Hash算法迭代次数
//        int times = 2;
//        // 得到hash后的密码
//        String encodePassword = new SimpleHash("md5", password, salt, times).toString();
//        // 储存用户信息，包括salt于hash后的密码
//        user.setSalt(salt);
//        user.setUserPassword(encodePassword);
//        userService.save(user);
//        return ResultFactory.buildSuccessResult(user);
//    }


    @GetMapping("/userLogout")
    @ApiOperation("用户的退出")
    public Result userLogout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        session.invalidate();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping("/export")
    @ApiOperation("批量导出用户信息")
    public void exportUser(HttpServletRequest request, HttpServletResponse response){
//        List list = userService.list(null);
//        System.out.println(list);
//        return UserExcelUtils.export(list);
        try{
            List<User> usersList = userService.list(null);
            String[] headerName = { "id","昵 称", "邮 箱", "电 话","性 别","地 址","用户等级","生 日","头像地址"};
            String[] headerKey = { "userId","userLevel", "email", "phoneNumber","sex","address","userLevel","birthday","avatar"};
            HSSFWorkbook wb = ExcelUtil.createExcel(headerName, headerKey, "用户信息管理表", usersList);
            if (wb == null) {
                return;
            }
            response.setContentType("application/vnd.ms-excel");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String str = sdf.format(date);
            String fileName = "用户信息管理" + str;
            response.setHeader("Content-disposition",
                    "attachment;filename=" + "User信息表" + ".xls");
            OutputStream ouputStream = response.getOutputStream();
            ouputStream.flush();
            wb.write(ouputStream);
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/find/{id}")
    @ApiOperation("根据id查找用户")
    public User getById(@PathVariable("id") int id){
        return userService.getById(id);
    }

    @GetMapping("/findAll/{page}/{size}")
    @ApiOperation("查找所有用户，实现分页")
    public IPage<User> getAll(@PathVariable("page") int page, @PathVariable("size") int size){
        return userService.getAll(page,size);
    }

    @PostMapping("/findByRequest")
    @ApiOperation("条件查询，实现分页，默认从第一页显示，每页显示10条记录")
    public IPage<User> getAllByRequest(@RequestBody User query){
        return userService.getAllByRequest(query);
    }

    @GetMapping("/findList/{field}/{page}/{size}")
    @ApiOperation("字段模糊查询")
    public IPage<User> getListUserByFuzzy(@PathVariable("field") String field, @PathVariable("page") int page, @PathVariable("size") int size){
        return userService.getListUserByFuzzy(field,page,size);
    }

    @PostMapping("/add")
    @ApiOperation("添加用户")
    public Boolean addUser(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation("删除用户")
    public Boolean delUser(@PathVariable("id") int id) {
        return userService.removeById(id);
    }

    @PutMapping("/edit")
    @ApiOperation("更新用户")
    public Boolean editUser(@RequestBody User user) {
        return userService.updateById(user);
    }

    @GetMapping("/verCode")
    @ApiOperation("验证码")
    public String verifyCode(HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        Result result = new Result();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            log.info(code);
            System.out.println("到底生成code没有哦" + code);
            // 将code存进Redis
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set("LoginCode", code);
            System.out.println("Redis中取一下验证码：" + operations.get("LoginCode"));
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
            return code;
        } catch (IOException e) {
            String msg = "wrong";
            log.info("", e);
            return msg;
        }
    }


}

