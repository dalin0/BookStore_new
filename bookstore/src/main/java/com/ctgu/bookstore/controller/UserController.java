package com.ctgu.bookstore.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.bookstore.entity.User;
import com.ctgu.bookstore.entity.VerifyCode;
import com.ctgu.bookstore.service.UserService;
import com.ctgu.bookstore.utils.ResultFactory;
import com.ctgu.bookstore.utils.UserExcelUtils;
import com.ctgu.bookstore.utils.authCode.IVerifyCodeGen;
import com.ctgu.bookstore.utils.authCode.SimpleCharVerifyCodeGenImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import com.ctgu.bookstore.entity.Result;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

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
    public Result login(@RequestBody  String json) {
        JSONObject v= JSONObject.parseObject(json);
        System.out.println(v);
        System.out.println("接收到的参数+" + v.get("email") + "         " + v.get("password") + "      " + v.get("verCode"));
        String email= v.getString("email");
        String password = v.getString("password");
        String verCode = v.getString("verCode");
        //从redis中取出验证码
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String LoginCode = operations.get("LoginCode");
        System.out.println("是不是取到了验证码哦woc" + LoginCode);
        Result result = new Result();
        User user = userService.getByEmail(email);
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email, password);
        System.out.println("封装后的token：" + usernamePasswordToken);
        System.out.println("试着取一下邮箱" + usernamePasswordToken.getUsername());
        // 执行登录方法
        System.out.println("前端接收到的验证码：" + verCode);
        if (!operations.get("LoginCode").equals(verCode)){
            result.setCode(404);
            result.setMsg("验证码错误");
            return result;
        }
        try {
            subject.login(usernamePasswordToken);
            result.setCode(0);
            result.setMsg("登录成功");
            result.setData(user);
            session.setAttribute("user", user);
            String token = UUID.randomUUID().toString();
            result.setToken(token);
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


    @PostMapping("/register")
    @ApiOperation("会员注册")
    public Result register(@RequestBody String json){
        System.out.println(json);
        JSONObject v= JSONObject.parseObject(json);
//        User newUser= JSON.toJavaObject(v,User.class);
        String email = v.getString("z_email");
        String password = v.getString("z_pass");
        email = HtmlUtils.htmlEscape(email);
        User newUser = new User();
        newUser.setEmail(email);
        boolean exist = userService.isExist(email);
        if (!exist) {
            String message = "用户名已被使用";
            return ResultFactory.buildFailResult(message);
        }
        newUser.setNickName(v.getString("z_user"));
        newUser.setPhoneNumber(v.getString("z_tel"));
        newUser.setUserPassword(v.getString("z_pass"));
        newUser.setName(v.getString("z_name"));
        newUser.setSex(v.getString("z_sex"));
        // 生成盐，默认长度为16位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 Hash算法迭代次数
        int times = 2;
        // 得到hash后的密码
        String encodePassword = new SimpleHash("md5", password, salt, times).toString();
        // 储存用户信息，包括salt于hash后的密码
        newUser.setSalt(salt);
        newUser.setUserPassword(encodePassword);
        userService.save(newUser);
        // 注册完成，进行登录操作
        Result result = new Result();
        User user = userService.getByEmail(email);
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email, password);
        // 执行登录方法
        try {
            subject.login(usernamePasswordToken);
            result.setCode(0);
            result.setMsg("登录成功");
            result.setData(user);
            session.setAttribute("user", user);
            String token = UUID.randomUUID().toString();
            result.setToken(token);
            return result;
        }catch (Exception e){
            result.setMsg("未知错误");
            return result;
        }
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
    public ResponseEntity<byte[]> exportUser(){
        List list = userService.list(null);
        return UserExcelUtils.export(list);
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

