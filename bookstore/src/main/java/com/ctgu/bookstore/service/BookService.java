package com.ctgu.bookstore.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.bookstore.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
public interface BookService extends IService<Book> {

    IPage<Book> getListPages(int page, int size);

    IPage<Book> getFuzzyPages(String fuzzy, int page, int size);

    Integer countByBookClass(int booktype);

}
