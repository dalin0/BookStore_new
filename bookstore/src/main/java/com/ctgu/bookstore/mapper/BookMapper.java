package com.ctgu.bookstore.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.bookstore.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

//    IPage<Book> selectBookPage(Page<Book> page, @Param(Constants.WRAPPER) Wrapper<Book> wrapper);
}
