package com.ctgu.bookstore.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.bookstore.entity.Book;
import com.ctgu.bookstore.entity.BookClass;
import com.ctgu.bookstore.entity.Result;
import com.ctgu.bookstore.mapper.BookMapper;
import com.ctgu.bookstore.service.BookService;
import com.ctgu.bookstore.utils.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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
@RequestMapping("/bookstore/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookMapper bookMapper;

    @PostMapping("/save")
    @ApiOperation("增加一条图书信息")
    public Result saveOneBook(@RequestBody Book book) {
        System.out.println(book);
        bookService.save(book);
        Result result = new Result();
        result.setCode(200);
        result.setMsg("添加成功");
        return result;
    }

    @DeleteMapping("/remove/{isbn}")
    @ApiOperation("删除指定图书的信息")
    public Result removeOneBook(@PathVariable("isbn") String isbn) {
        Result result = new Result();
        bookService.removeById(isbn);
        result.setCode(200);
        result.setMsg("删除成功");
        return result;
    }

    @GetMapping("/get/{isbn}")
    @ApiOperation("获取从前端传过来的ISBN，返回这本书的信息")
    public Result getOneBook(@PathVariable("isbn") String isbn) {
        Book book = bookService.getById(isbn);
        System.out.println(book);
        Result result = new Result();
        if(book!=null){
            result.setCode(0);
            result.setData(book);
        }else{
            result.setCode(1);
        }
        return result;
    }

    @PostMapping("/update")
    @ApiOperation("获取到指定图书，然后对其信息进行修改")
    public Result updateOneBook(@RequestBody Book book) {
        System.out.println("11111111111111111" + book);
        Result result = new Result();
        bookService.updateById(book);
        result.setCode(200);
        result.setMsg("更新成功");
        return result;
    }

    @GetMapping("/listBook")
    @ApiOperation("查询所有图书的信息")
    public List<Book> listBooks() {
        List<Book> books = bookMapper.selectList(null);
        return books;
    }

    @GetMapping("/listTopBook")
    @ApiOperation("查询所有畅销图书的信息")
    public Result listTopBooks() {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.orderByDesc("sale_num");
        List<Book> books = bookMapper.selectList(wrapper);
        Result result =new Result();
        if(books!=null){
            result.setCode(0);
            result.setData(books);
        }else{
            result.setCode(1);
        }
        return result;
    }

    @GetMapping("/getFuzzy/{keyword}")
    @ApiOperation("图书名称、作者模糊查询")
    public Result getFuzzy(@PathVariable("keyword") String keyword){
        List<Book> books = this.bookMapper.selectList(new QueryWrapper<Book>()
        .like("book_name", keyword).or().like("author", keyword));
        Result result =new Result();
        if(books!=null){
            result.setCode(0);
            result.setData(books);
        }else{
            result.setCode(1);
        }
        return result;
    }

    @GetMapping("/listPages/{page}/{size}")
    @ApiOperation("实现分页查询")
    public IPage<Book> getListPages(@PathVariable("page")  int page, @PathVariable("size") int size){
        System.out.println(" 页数" + page + "     " + "size");
        return bookService.getListPages(page, size);
    }

    @GetMapping("/listFuzzy/{fuzzy}/{page}/{size}")
    @ApiOperation("全字段模糊查询分页")
    public IPage<Book> getFuzzyPages(@PathVariable("fuzzy")String fuzzy,
                                     @PathVariable("page")int page,
                                     @PathVariable("size")int size){
        return bookService.getFuzzyPages(fuzzy, page, size);
    }

    /**
     * 导入用户数据
     * @return
     */
    @PostMapping("/import")
    @ApiOperation("批量导入Book信息")
    public String uploadExcel(@RequestParam("file") MultipartFile file,
                              Map<String, Object> map) {
        String name = file.getOriginalFilename();
        List<Book> list = null;
        try {
            list = ExcelUtil.excelToShopIdList(file.getInputStream());
            if (list == null || list.size() <= 0) {
                return "导入的数据为空";
            }
            //excel的数据保存到数据库
            try {
                for (Book excel : list) {
                    bookService.save(excel);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "保存成功";
    }
    @GetMapping("/exportBooks")
    @ApiOperation("导出Book信息到Excel中")
    public void exportBooks(HttpServletRequest request, HttpServletResponse response){
        try{
            List<Book> voList = bookService.list(null);
            String[] headerName = { "图书编号","书 名", "图 片", "作 者","价 格","简 介","类 型","库 存","出版社","收藏量","月销量","出版日期"};
            String[] headerKey = { "isbn","bookName", "bookPicture", "author","price","description","bookType","repertory","press","cllectNum","saleNum","publicDate"};
            HSSFWorkbook wb = ExcelUtil.createExcel(headerName, headerKey, "报名信息管理表", voList);
            if (wb == null) {
                return;
            }
            response.setContentType("application/vnd.ms-excel");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String str = sdf.format(date);
            String fileName = "学生信息管理" + str;
            response.setHeader("Content-disposition",
                    "attachment;filename=" + "Book信息表" + ".xls");
            OutputStream ouputStream = response.getOutputStream();
            ouputStream.flush();
            wb.write(ouputStream);
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/top5")
    @ApiOperation("月销排名前5的图书名")
    public Map<String,List> listTop5(){
        List<String> slist=new ArrayList<>();
        List<Integer> ilist=new ArrayList<>();
        QueryWrapper<Book> wrapper =new QueryWrapper<Book>();
        wrapper.select("book_name","sale_num")
                .orderByDesc("sale_num")
                .last("limit 5");
        List<Book> list=  bookService.list(wrapper);
        for (Book b:list) {
            slist.add(b.getBookName());
            ilist.add(b.getSaleNum());
        }
        Map<String,List> mp = new HashMap<>();
        mp.put("cp",slist);
        mp.put("product",ilist);
        return mp;
    }

    @GetMapping("/getBookInfo")
    public List<BookClass> getBookInfo() {
        BookClass bookClass = new BookClass();
        // 查找所有类型为1的图书信息
        Book book = new Book();
        book.setBookType(1);
        QueryWrapper<Book> qw = new QueryWrapper<>(book);
        List<Book> bookList = bookMapper.selectList(qw);
        bookClass.setBooks(bookList);
        bookClass.setClassname("教育");
        // 查找所有类型为2的图书信息
        Book book1 = new Book();
        book1.setBookType(2);
        BookClass bookClass1 = new BookClass();
        QueryWrapper<Book> qw1 = new QueryWrapper<>(book1);
        List<Book> bookList1 = bookMapper.selectList(qw1);
        bookClass1.setBooks(bookList1);
        bookClass1.setClassname("小说");
        // 查找所有类型为3的图书信息
        Book book2 = new Book();
        book2.setBookType(3);
        BookClass bookClass2 = new BookClass();
        QueryWrapper<Book> qw2 = new QueryWrapper<>(book2);
        List<Book> bookList2 = bookMapper.selectList(qw2);
        bookClass2.setBooks(bookList2);
        bookClass2.setClassname("文艺");
        // 查找所有类型为4的图书信息
        Book book3 = new Book();
        book3.setBookType(4);
        BookClass bookClass3 = new BookClass();
        QueryWrapper<Book> qw3 = new QueryWrapper<>(book3);
        List<Book> bookList3 = bookMapper.selectList(qw3);
        bookClass3.setBooks(bookList3);
        bookClass3.setClassname("专业书");
        // 查找所有类型为5的图书信息
        Book book4 = new Book();
        book4.setBookType(5);
        BookClass bookClass4 = new BookClass();
        QueryWrapper<Book> qw4 = new QueryWrapper<>(book4);
        List<Book> bookList4 = bookMapper.selectList(qw4);
        bookClass4.setBooks(bookList4);
        bookClass4.setClassname("动漫");
        // 查找所有类型为6的图书信息
        Book book5 = new Book();
        book5.setBookType(6);
        BookClass bookClass5 = new BookClass();
        QueryWrapper<Book> qw5 = new QueryWrapper<>(book5);
        List<Book> bookList5 = bookMapper.selectList(qw5);
        bookClass5.setBooks(bookList5);
        bookClass5.setClassname("童书");
        // 查找所有类型为7的图书信息
        Book book6 = new Book();
        book6.setBookType(7);
        BookClass bookClass6 = new BookClass();
        QueryWrapper<Book> qw6 = new QueryWrapper<>(book6);
        List<Book> bookList6 = bookMapper.selectList(qw6);
        bookClass6.setBooks(bookList6);
        bookClass6.setClassname("人文社科");
        // 查找所有类型为8的图书信息
        Book book7 = new Book();
        book7.setBookType(8);
        BookClass bookClass7 = new BookClass();
        QueryWrapper<Book> qw7 = new QueryWrapper<>(book7);
        List<Book> bookList7 = bookMapper.selectList(qw7);
        bookClass7.setBooks(bookList7);
        bookClass7.setClassname("经管");
        // 查找所有类型为9的图书信息
        Book book8 = new Book();
        book8.setBookType(9);
        BookClass bookClass8 = new BookClass();
        QueryWrapper<Book> qw8 = new QueryWrapper<>(book8);
        List<Book> bookList8 = bookMapper.selectList(qw8);
        bookClass8.setBooks(bookList8);
        bookClass8.setClassname("成功励志类");
        // 查找所有类型为10的图书信息
        Book book9 = new Book();
        book9.setBookType(10);
        BookClass bookClass9 = new BookClass();
        QueryWrapper<Book> qw9 = new QueryWrapper<>(book9);
        List<Book> bookList9 = bookMapper.selectList(qw9);
        bookClass9.setBooks(bookList9);
        bookClass9.setClassname("生活");
        // 查找所有类型为11的图书信息
        Book book10 = new Book();
        book10.setBookType(11);
        BookClass bookClass10 = new BookClass();
        QueryWrapper<Book> qw10 = new QueryWrapper<>(book10);
        List<Book> bookList10 = bookMapper.selectList(qw10);
        bookClass10.setBooks(bookList10);
        bookClass10.setClassname("科技");
        List<BookClass> list = new ArrayList<>();
        list.add(bookClass);
        list.add(bookClass1);
        list.add(bookClass2);
        list.add(bookClass3);
        list.add(bookClass4);
        list.add(bookClass5);
        list.add(bookClass6);
        list.add(bookClass7);
        list.add(bookClass8);
        list.add(bookClass9);
        list.add(bookClass10);
        return list;
    }


}

