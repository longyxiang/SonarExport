package com.lyx.sonar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelForXSSF {
    private static Log log = LogFactory.getLog(WriteExcelForXSSF.class);

    public void write(List<String> projectList, Map<String, Map<String, Map>> dataMap, String startTime, String endTime)
        throws ParseException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("0");
        Row row = sheet.createRow(0);
        sheet.setColumnWidth(0, 40 * 256);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long m = sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime();
        long day = m / (1000 * 60 * 60 * 24);
        workbook.setSheetName(0, "AnalyzeReport");
        row.createCell(0).setCellStyle(cellStyle);
        row.createCell(0).setCellValue("ProjectName");
        row.createCell(1).setCellStyle(cellStyle);
        row.createCell(1).setCellValue("bugType");
        for (int i = 0; i <= day; i++) {
            // 转换时间
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dd = df.parse(endTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dd);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            String date = df.format(calendar.getTime());
            row.createCell(i + 2).setCellStyle(cellStyle);
            row.createCell(i + 2).setCellValue(date);
            sheet.setColumnWidth(i + 2, 18 * 256);
        }

        // for (int j = 0; j < projectList.size(); j++) {
        //
        // Map<String, Map> dataTypeMap = dataMap.get(projectList.get(j));
        // int size = dataTypeMap.size();
        // Set<Map.Entry<String, Map>> entries = dataTypeMap.entrySet();
        // for (Map.Entry<String, Map> entry : entries) {
        // Row rowNum = sheet.createRow(j + 1);
        // System.out.println(entry.getKey() + "," + entry.getValue());
        //
        // rowNum.createCell(0).setCellValue(projectList.get(j));
        // for (int k = 1; k <= day + 1; k++) {
        // DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // Date dd = df.parse(endTime);
        // Calendar calendar = Calendar.getInstance();
        // calendar.setTime(dd);
        // calendar.add(Calendar.DAY_OF_MONTH, -k);
        // String dateTime = df.format(calendar.getTime());
        // Map<String, String> bugMap = dataTypeMap.get(projectList.get(j));
        // if ((bugMap.get(dateTime) != null) && (bugMap.get(dateTime) != "")) {
        // int num = Integer.valueOf(bugMap.get(dateTime));
        // rowNum.createCell(k).setCellValue(num);
        // }
        // }
        //
        // }
        //
        // }

        Set<Map.Entry<String, Map<String, Map>>> entrySet = dataMap.entrySet();
        int j = 0;
        for (Map.Entry<String, Map<String, Map>> en : entrySet) {
            String name = en.getKey();
            Map<String, Map> dataTypeMap = en.getValue();
            Set<Map.Entry<String, Map>> entries = dataTypeMap.entrySet();
            for (Map.Entry<String, Map> entry : entries) {
                Row rowNum = sheet.createRow(j + 1);
                rowNum.createCell(0).setCellValue(name);
                rowNum.createCell(1).setCellValue(entry.getKey());
                for (int k = 2; k <= day + 1; k++) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date dd = df.parse(endTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dd);
                    calendar.add(Calendar.DAY_OF_MONTH, 2 - k);
                    String dateTime = df.format(calendar.getTime());
                    Map<String, String> bugMap = entry.getValue();
                    if ((bugMap.get(dateTime) != null) && (bugMap.get(dateTime) != "")) {
                        int num = Integer.valueOf(bugMap.get(dateTime));
                        rowNum.createCell(k).setCellValue(num);
                    }
                }
                j++;
            }
        }

        try {
            Calendar cal = Calendar.getInstance();
            String date, daytime, month, year;
            year = String.valueOf(cal.get(Calendar.YEAR));
            month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            daytime = String.valueOf(cal.get(Calendar.DATE));
            date = year + "-" + month + "-" + daytime;
            File file = new File("AnalyzeReport_" + date + ".xlsx");
            log.info("Report Path:" + file.getPath());
            FileOutputStream fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            fileoutputStream.close();
        } catch (IOException e) {
            log.error("Export Report Error");
        }
    }

    public void write1(List<String> projectList, Map<String, Map<String, String>> dataMap, String startTime,
        String endTime) throws ParseException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("0");
        Row row = sheet.createRow(0);
        sheet.setColumnWidth(0, 40 * 256);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        workbook.setSheetName(0, "AnalyzeReport");
        row.createCell(0).setCellStyle(cellStyle);
        row.createCell(0).setCellValue("ProjectName");
        row.createCell(1).setCellStyle(cellStyle);
        row.createCell(1).setCellValue("Total");
        row.createCell(2).setCellStyle(cellStyle);
        row.createCell(2).setCellValue("阻断");
        row.createCell(3).setCellStyle(cellStyle);
        row.createCell(3).setCellValue("严重");
        row.createCell(4).setCellStyle(cellStyle);
        row.createCell(4).setCellValue("主要");
        row.createCell(5).setCellStyle(cellStyle);
        row.createCell(5).setCellValue("次要");
        row.createCell(6).setCellStyle(cellStyle);
        row.createCell(6).setCellValue("提示");
        row.createCell(7).setCellStyle(cellStyle);
        row.createCell(7).setCellValue("超过80行方法体个数");
        Set<Map.Entry<String, Map<String, String>>> entrySet = dataMap.entrySet();
        int j = 0;
        for (Map.Entry<String, Map<String, String>> en : entrySet) {
            Row rowNum = sheet.createRow(j + 1);
            String name = en.getKey();
            rowNum.createCell(0).setCellValue(name);
            Map<String, String> dataTypeMap = en.getValue();
            Set<Map.Entry<String, String>> entries = dataTypeMap.entrySet();
            boolean isFlag = false;
            for (Map.Entry<String, String> entry : entries) {
                if ("BLOCKER".equals(entry.getKey())) {
                    // 阻断
                    rowNum.createCell(2).setCellStyle(cellStyle);
                    rowNum.createCell(2).setCellValue(entry.getValue());
                } else if ("CRITICAL".equals(entry.getKey())) {
                    // 严重
                    rowNum.createCell(3).setCellStyle(cellStyle);
                    rowNum.createCell(3).setCellValue(entry.getValue());
                } else if ("MAJOR".equals(entry.getKey())) {
                    // 主要
                    rowNum.createCell(4).setCellStyle(cellStyle);
                    rowNum.createCell(4).setCellValue(entry.getValue());
                } else if ("MINOR".equals(entry.getKey())) {
                    // 次要
                    rowNum.createCell(5).setCellStyle(cellStyle);
                    rowNum.createCell(5).setCellValue(entry.getValue());
                } else if ("INFO".equals(entry.getKey())) {
                    // 提示
                    rowNum.createCell(6).setCellStyle(cellStyle);
                    rowNum.createCell(6).setCellValue(entry.getValue());
                } else if ("projectUuids".equals(entry.getKey())) {
                    // 超过80行方法体个数
                    rowNum.createCell(7).setCellStyle(cellStyle);
                    rowNum.createCell(7).setCellValue(entry.getValue());
                    isFlag = true;
                }
            }
            if (!isFlag) {
                rowNum.createCell(7).setCellStyle(cellStyle);
                rowNum.createCell(7).setCellValue("0");
            }
            j++;
        }

        try {
            Calendar cal = Calendar.getInstance();
            String date, daytime, month, year;
            year = String.valueOf(cal.get(Calendar.YEAR));
            month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            daytime = String.valueOf(cal.get(Calendar.DATE));
            date = year + "-" + month + "-" + daytime;
            File file = new File("AnalyzeReport_bug_" + date + ".xlsx");
            log.info("Report Path:" + file.getPath());
            FileOutputStream fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            fileoutputStream.close();
        } catch (IOException e) {
            log.error("Export Report Error");
        }
    }
}