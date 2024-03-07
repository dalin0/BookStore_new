package com.ctgu.bookstore.controller;


import com.ctgu.bookstore.entity.Book;
import com.ctgu.bookstore.entity.Orderdetail;
import com.ctgu.bookstore.entity.ResultOrderDetail;
import com.ctgu.bookstore.service.BookService;
import com.ctgu.bookstore.service.OrderdetailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bookstore/orderdetail")
public class OrderdetailController {

    @Autowired
    private OrderdetailService orderdetailService;

    @Autowired
    private BookService bookService;


    @GetMapping("/findByOrderId/{id}")
    @ApiOperation("根据订单找到订单详情")
    public List<ResultOrderDetail> findByOrderID(@PathVariable("id")int orderId){
        // 根据Id查询到多个订单详情并返回
        ResultOrderDetail rest;
        List<Orderdetail> orderdetails = orderdetailService.getListById(orderId);
        List<ResultOrderDetail> res = new ArrayList<>();
        for(Orderdetail item: orderdetails){
            rest = new ResultOrderDetail();
            rest.setBookName(bookService.getById(item.getIsbn()).getBookName());
            rest.setNum(item.getNum());
            rest.setPrice(item.getPrice());
            rest.setBookPicture(bookService.getById(item.getIsbn()).getBookPicture());
            System.out.println(rest);
            res.add(rest);
        }
        return res;
    }
}

