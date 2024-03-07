package com.ctgu.bookstore.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Comment对象", description="特殊图书列表")
public class BookClass {

    private static final long serialVersionUID = 1L;

    private String classname;

    private List<Book> books;
}
