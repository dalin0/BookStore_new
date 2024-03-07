package com.ctgu.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.bookstore.entity.Book;
import com.ctgu.bookstore.mapper.BookMapper;
import com.ctgu.bookstore.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;


    @Override
    public IPage<Book> getListPages(int page, int size) {
        IPage<Book> userIPage = bookMapper.selectPage(new Page<>(page, size), null);
        return userIPage;
    }

    @Override
    public IPage<Book> getFuzzyPages(String name, int page, int size) {
        QueryWrapper<Book> userIPage = new QueryWrapper<>();
        userIPage.like("ISBN", name).or()
                .like("book_name", name).or()
                .like("author", name).or()
                .like("price", name).or()
                .like("description", name).or()
                .like("book_type", name).or()
                .like("repertory", name).or()
                .like("press", name).or()
                .like("cllect_num", name).or()
                .like("sale_num", name).or()
                .like("public_date", name);

        IPage<Book> bookIPage = bookMapper.selectPage(new Page<>(page, size), userIPage);
        return bookIPage;
    }

    @Override
    public Integer countByBookClass(int booktype) {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.eq("book_type",booktype);
        return bookMapper.selectCount(wrapper);
    }


}
