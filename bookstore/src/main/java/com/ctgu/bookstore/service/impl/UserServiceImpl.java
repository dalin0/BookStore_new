package com.ctgu.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.bookstore.entity.User;
import com.ctgu.bookstore.mapper.UserMapper;
import com.ctgu.bookstore.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: bookstore
 * @description:
 * @author: Nidol
 * @create: 2024-3-6
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByEmail(String email) {
        User userCondition = new User();
        userCondition.setEmail(email);
        QueryWrapper<User> qw = new QueryWrapper<>(userCondition);
        return userMapper.selectOne(qw);
    }

    @Override
    public IPage<User> getListUserByFuzzy(String field, int page, int size) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name",field).or()
                .like("email",field).or()
                .like("user_id",field).or()
                .like("nick_name",field).or()
                .like("sex",field).or()
                .like("address",field);
        List<User> users = userMapper.selectList(wrapper);
        if (field != null){
            wrapper.like("name",field).or()
                    .like("email",field).or()
                    .like("user_id",field).or()
                    .like("nick_name",field).or()
                    .like("sex",field).or()
                    .like("address",field);
        }
        IPage<User> userIPage = userMapper.selectPage(new Page<>(page, size), wrapper);
        return userIPage;
    }

    @Override
    public IPage<User> getAll(int page, int size) {
        IPage<User> userIPage = userMapper.selectPage(new Page<>(page, size), null);
        return userIPage;
    }

    @Override
    public IPage<User> getAllByRequest(User query) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (query.getUserId() != null) {
            wrapper.like("user_id",query.getUserId());
        }
        if (query.getUserLevel() != null) {
            wrapper.like("user_level",query.getUserLevel());
        }
        if(query.getEmail() != null) {
            wrapper.like("email",query.getEmail());
        }
        if(query.getNickName() != null) {
            wrapper.like("nick_name",query.getNickName());
        }
        if(query.getPhoneNumber() != null) {
            wrapper.like("phone_number",query.getPhoneNumber());
        }
        if(query.getName() != null) {
            wrapper.like("name",query.getName());
        }
        if(query.getBirthday() != null) {
            wrapper.like("birthday",query.getBirthday());
        }
        if(query.getAddress() != null) {
            wrapper.like("address",query.getAddress());
        }
        if(query.getSex() != null) {
            wrapper.like("sex",query.getSex());
        }
        return userMapper.selectPage(new Page<>(1,10), wrapper);
    }

    @Override
    public boolean isExist(String email) {
        User user = new User();
        user.setEmail(email);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        User user1 = userMapper.selectOne(queryWrapper);
        System.out.println("看看看看看看看新注册的用户是否已经存在数据库： " + user1);
        if (user1 != null){
            return false;
        }else {
            return true;
        }
    }
}
