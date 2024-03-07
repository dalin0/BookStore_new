package com.ctgu.bookstore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.bookstore.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 * @author Nidol
 */
@Transactional
public interface UserService extends IService<User> {

    IPage<User> getListUserByFuzzy(String field,int page,int size);

    User getByEmail(String email);

    IPage<User> getAll(int page, int size);

    IPage<User> getAllByRequest(User query);

    boolean isExist(String email);
}
