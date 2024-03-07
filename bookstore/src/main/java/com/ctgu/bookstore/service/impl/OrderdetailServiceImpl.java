package com.ctgu.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.bookstore.entity.Orderdetail;
import com.ctgu.bookstore.mapper.OrderdetailMapper;
import com.ctgu.bookstore.service.OrderdetailService;
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
public class OrderdetailServiceImpl extends ServiceImpl<OrderdetailMapper, Orderdetail> implements OrderdetailService {

    @Autowired
    private OrderdetailMapper orderdetailMapper;

    @Override
    public List<Orderdetail> getListById(int orderId) {
        List<Orderdetail> res = orderdetailMapper.selectList(new QueryWrapper<Orderdetail>()
        .eq("order_id", orderId));
        return res;

    }

}
