package com.ctgu.bookstore.controller;


import com.ctgu.bookstore.entity.Bookclassify;
import com.ctgu.bookstore.entity.Result;
import com.ctgu.bookstore.entity.ResultBookclassify;
import com.ctgu.bookstore.service.BookService;
import com.ctgu.bookstore.service.BookclassifyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/bookstore/bookclassify")
public class BookclassifyController {
    @Autowired
    private BookclassifyService bookclassifyService;
    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    @ApiOperation("获取所有图书分类")
    public Result listBookclassify(){
        List<Bookclassify> list=bookclassifyService.list(null);
        Result result = new Result();
        if (list!=null){
            result.setCode(0);
            result.setData(list);
        }else {
            result.setCode(1);
        }
        return result;
    }

    @GetMapping("/allname")
    @ApiOperation("获取所有图书分类名称")
    public List<String> listBookclassifyName(){
        List<String> list=bookclassifyService.listClassName();
        return list;
    }

    @GetMapping("/nameandcnt")
    @ApiOperation("获取图书分类名称和数量")
    public List<ResultBookclassify> listBookclassifyNameandCount(){
        List<Bookclassify> slist=bookclassifyService.list(null);
        List<ResultBookclassify> list=new ArrayList<>();

        for (Bookclassify x:slist
             ) {
            ResultBookclassify res = new ResultBookclassify();
            res.setValue(bookService.countByBookClass(x.getClassId()));
            res.setName(x.getClassName());

            list.add(res);
        }
        for (ResultBookclassify x:
            list ) {
            System.out.println(x);
        }
        return list;
    }
}


