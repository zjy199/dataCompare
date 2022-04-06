package com.thxh.datacompare.utils;

import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author YYY
 * @date 2021年12月30日20:14
 */
public class DataToExcel {

    public static void createExcel(String excelName,List<HashMap<String,Object>> mapArrayList){
/*
        System.out.println("数据加载...");
        List<Student> list = new ArrayList<>();
        Student s1 = new Student("张三", 22, "男");
        Student s2 = new Student("李四", 22, "男");
        Student s3 = new Student("王五", 22, "男");
        Student s4 = new Student("赵敏", 22, "女");
        Student s5 = new Student("张无忌", 22, "男");
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        System.out.println("数据加载完成...");*/


        System.out.println("数据转成Excel...");
        // 定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 创建一个Sheet页
        XSSFSheet sheet = wb.createSheet("First sheet");
        //设置行高
        sheet.setDefaultRowHeight((short) (2 * 256));

        // YYY
        int arrayListSize = 0;
        int iternum = 0;
        if (mapArrayList.size() > 0){
            arrayListSize = mapArrayList.get(0).keySet().size();
        }
        for (int i = 0;i < arrayListSize;i++){
            sheet.setColumnWidth(i,4000);
        }
        //设置列宽
//        sheet.setColumnWidth(0, 4000);
//        sheet.setColumnWidth(1, 4000);
//        sheet.setColumnWidth(2, 4000);
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        //获得表格第一行
        XSSFRow row = sheet.createRow(0);
        // YYY
        Iterator iterator = mapArrayList.get(0).keySet().iterator();
        while (iterator.hasNext()){
                XSSFCell cell = row.createCell(iternum);
                cell.setCellValue((String) iterator.next());
                iternum++;
        }
        //根据需要给第一行每一列设置标题
//        XSSFCell cell = row.createCell(0);
//        cell.setCellValue("id");
//        cell = row.createCell(1);
//        cell.setCellValue("name");
//        cell = row.createCell(2);
//        cell.setCellValue("age");
        XSSFRow rows;
        XSSFCell cells;
        //循环拿到的数据给所有行每一列设置对应的值
        for (int i = 0; i < mapArrayList.size(); i++) {

            // 在这个sheet页里创建一行
            rows = sheet.createRow(i + 1);
            // YYY
            int size = mapArrayList.get(0).keySet().size();
//            Object o = mapArrayList.get(0)
            System.out.println(size);
            System.out.println("************");
            for (int j = 0;j < size;j++){
                System.out.println(j);
                System.out.println(mapArrayList.get(0).keySet().toArray()[j]);
                String tt = mapArrayList.get(i).get(mapArrayList.get(0).keySet().toArray()[j]).toString();
                System.out.println("--------");
                System.out.println(tt);
                cells = rows.createCell(j);
                cells.setCellValue(tt);
            }
            // 该行创建一个单元格,在该单元格里设置值
//            String name = mapArrayList.get(i).get("id").toString();
//            String age = (String) mapArrayList.get(i).get("name");
//            String sex = mapArrayList.get(i).get("age").toString();
//            cells = rows.createCell(0);
//            cells.setCellValue(name);
//            cells = rows.createCell(1);
//            cells.setCellValue(age);
//            cells = rows.createCell(2);
//            cells.setCellValue(sex);
        }
        try {
            File file = new File(excelName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
            wb.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

/*
    public static void main(String[] args) {
        createExcel("d:/aaaa.xls",);
    }
*/



}
