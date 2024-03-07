package com.ctgu.bookstore;

import com.ctgu.bookstore.entity.Book;
import com.ctgu.bookstore.mapper.BookMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: xuzhen
 * @Description:
 * @Date: Created in 13:49 2020/6/3
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- select method test ------"));
        Book book = bookMapper.selectById("7-5600-3879-4");
        System.out.println(book);
    }

}