package com.ctgu.bookstore.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.bookstore.entity.Orderlist;
import com.ctgu.bookstore.entity.Result;
import com.ctgu.bookstore.service.OrderlistService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/bookstore/orderlist")
public class OrderlistController {

    @Autowired
    private OrderlistService orderlistService;

    @GetMapping("/total")
    @ApiOperation("获取订单数量")
    public int getTotalOrder(){
        return orderlistService.list(null).size();
    }

    @GetMapping("/total/amount")
    @ApiOperation("获取总营业额")
    public double getTotalAmount(){
        return orderlistService.getTotalAmount();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除一个订单")
    public Result deleteOrderlist(@PathVariable("id") int orderId){
        Result res = new Result();
        orderlistService.removeById(orderId);
        res.setCode(1);
        return res;
    }

    @PutMapping("/update/{id}")
    @ApiOperation("订单审核，使订单状态变为true")
    public Result updateOrderlist(@PathVariable("id") int orderId){
        Orderlist orderlist = new Orderlist();
        orderlist.setOrderId(orderId);
        orderlist.setStatus(1);
        Result res = new Result();
        orderlistService.updateById(orderlist);
        res.setCode(1);
        return res;
    }

    @GetMapping("/select/{id}")
    @ApiOperation("根据订单编号查询订单")
    public Orderlist selectOneOrderlist(@PathVariable("id") int orderId){
        Orderlist oneOrder = orderlistService.getById(orderId);
        System.out.println(oneOrder);
        return oneOrder;
    }

    @GetMapping("/selectAll/{page}/{size}")
    @ApiOperation("显示所有订单，并分页显示")
    public IPage<Orderlist> selectAllOrderlist(@PathVariable("page")int page, @PathVariable("size")int size){
        return orderlistService.getAll(page,size);
    }

    @GetMapping("/selectList/{fuzzy}/{page}/{size}")
    @ApiOperation("模糊查询并分页显示")
    public IPage<Orderlist> selectByFuzzy(@PathVariable("fuzzy")String fuzzy,
                                          @PathVariable("page")int page,
                                          @PathVariable("size")int size){
        return orderlistService.getListByFuzzy(fuzzy,page,size);

    }

    @PostMapping("/add")
    @ApiOperation("添加订单")
    public Result addOrder(@RequestBody String json ){
        System.out.println(json);
        JSONObject v= JSONObject.parseObject(json);
        System.out.println(v.getString("orderlist"));
        JSONObject orderlistjson = JSONObject.parseObject(v.getString("orderlist"));
        Orderlist orderlist = JSON.toJavaObject(orderlistjson,Orderlist.class);
        System.out.println(orderlist);
        boolean save = orderlistService.save(orderlist);
        Result result = new Result();
        if(save == true){
            result.setCode(0);
            result.setMsg("添加成功");

        }else {
            result.setCode(1);
            result.setMsg("添加失败");
        }
        return result;
    }
}

