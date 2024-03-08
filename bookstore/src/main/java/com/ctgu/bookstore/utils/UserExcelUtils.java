package com.ctgu.bookstore.utils;

import com.ctgu.bookstore.entity.Employee;
import com.ctgu.bookstore.entity.User;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @program: BookStore
 * @description:
 * @author: Nidol
 * @create: 2024-3-6
 **/
public class UserExcelUtils {

    public static ResponseEntity<byte[]> export(List<User> userList) {
        //1.创建一个excel文档
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
         //2.创建文档摘要
        hssfWorkbook.createInformationProperties();
        //3.获取并配置文档摘要信息
        DocumentSummaryInformation docInfo = hssfWorkbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("用户信息");
        //文档管理员
        docInfo.setManager("Nidol");
        //文档所属公司
        docInfo.setCompany("个人");
        //文档版本
        docInfo.setApplicationVersion(1);
        //4.获取文档摘要信息
        SummaryInformation summaryInformation = hssfWorkbook.getSummaryInformation();
        //文档标题
        summaryInformation.setAuthor("hopec");
        //文档创建时间
        summaryInformation.setCreateDateTime(new Date());
        //文档备注
        summaryInformation.setComments("文档备注");
        //5.创建样式
        //创建标题行的样式
        HSSFCellStyle headerStyle = hssfWorkbook.createCellStyle();
        //设置该样式的图案颜色为黄色
//        headerStyle.setFillForegroundColor(IndexedColors.GREEN.index);//设置图案颜色
//        headerStyle.setFillBackgroundColor(IndexedColors.RED.index);//设置图案背景色
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        //设置图案填充的样式
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置日期相关的样式
        HSSFCellStyle dateCellStyle = hssfWorkbook.createCellStyle();
        //这里的m/d/yy 相当于yyyy-MM-dd
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = hssfWorkbook.createSheet("用户信息表");
        //设置每一列的宽度
        sheet.setColumnWidth(0,5*256);
        sheet.setColumnWidth(1,12*256);
        sheet.setColumnWidth(2,10*256);
        sheet.setColumnWidth(3,5*256);
        sheet.setColumnWidth(4,16*256);
        sheet.setColumnWidth(5,20*256);
        sheet.setColumnWidth(6,10*256);
        sheet.setColumnWidth(7,10*256);
        sheet.setColumnWidth(8,18*256);
        sheet.setColumnWidth(9,12*256);
        //6.创建标题行
        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("编号");
        c0.setCellStyle(headerStyle);
        HSSFCell c1 = r0.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("用户名");
        HSSFCell c2 = r0.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("邮箱");
        HSSFCell c3 = r0.createCell(3);
        c3.setCellStyle(headerStyle);
        c3.setCellValue("性别");
        HSSFCell c4 = r0.createCell(4);
        c4.setCellStyle(headerStyle);
        c4.setCellValue("出生日期");
        HSSFCell c5 = r0.createCell(5);
        c5.setCellStyle(headerStyle);
        c5.setCellValue("电话");
        HSSFCell c6 = r0.createCell(6);
        c6.setCellStyle(headerStyle);
        c6.setCellValue("用户等级");
        HSSFCell c7 = r0.createCell(7);
        c7.setCellStyle(headerStyle);
        c7.setCellValue("积分");
        HSSFCell c8 = r0.createCell(8);
        c8.setCellStyle(headerStyle);
        c8.setCellValue("地址");
        HSSFCell c9 = r0.createCell(9);
        c9.setCellStyle(headerStyle);
        c9.setCellValue("头像地址链接");
        HSSFCell c10 = r0.createCell(10);

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            HSSFRow row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(user.getUserId());
            row.createCell(1).setCellValue(user.getNickName());
            row.createCell(2).setCellValue(user.getEmail());
            row.createCell(3).setCellValue(user.getSex());
            HSSFCell cell4 = row.createCell(4);
            //单独设置日期的样式
            cell4.setCellStyle(dateCellStyle);
            cell4.setCellValue(user.getBirthday());
            row.createCell(5).setCellValue(user.getPhoneNumber());
            row.createCell(6).setCellValue(user.getUserLevel());
            row.createCell(7).setCellValue(user.getIntegral());
            row.createCell(8).setCellValue(user.getAddress());
            row.createCell(9).setCellValue(user.getAvatar());
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
        //将数据表这几个中文的字转码 防止导出后乱码
            headers.setContentDispositionFormData("attachment",
                    new String("UserInfo.xls".getBytes("UTF-8"),"ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            hssfWorkbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(stream.toByteArray(),headers, HttpStatus.CREATED);
    }
}
