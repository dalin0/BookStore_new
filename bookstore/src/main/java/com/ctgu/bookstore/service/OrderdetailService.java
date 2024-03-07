package com.ctgu.bookstore.service;

import com.ctgu.bookstore.entity.Orderdetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
public interface OrderdetailService extends IService<Orderdetail> {

    List<Orderdetail> getListById(int id);

}
