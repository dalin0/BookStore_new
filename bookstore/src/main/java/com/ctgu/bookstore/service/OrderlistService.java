package com.ctgu.bookstore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.bookstore.entity.Orderlist;
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
public interface OrderlistService extends IService<Orderlist> {


    IPage<Orderlist> getListByFuzzy(String fuzzy, int page, int size);

    IPage<Orderlist> getAll(int page, int size);

    double getTotalAmount();
}
