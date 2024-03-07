package com.ctgu.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.bookstore.entity.Bookclassify;
import com.ctgu.bookstore.mapper.BookclassifyMapper;
import com.ctgu.bookstore.service.BookclassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class BookclassifyServiceImpl extends ServiceImpl<BookclassifyMapper, Bookclassify> implements BookclassifyService {

    @Autowired
    private BookclassifyMapper bookclassifyMapper;

    @Override
    public List<String> listClassName() {
        QueryWrapper<Bookclassify> wrapper = new QueryWrapper<>();
        wrapper.select("class_name");
        List<Bookclassify> list=bookclassifyMapper.selectList(wrapper);
        List<String> stringList=new ArrayList<String>();
        for (Bookclassify x:list){
            stringList.add(x.getClassName());
        }
        return stringList;
    }
}
