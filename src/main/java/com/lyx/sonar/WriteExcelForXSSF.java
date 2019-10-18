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
                    calendar.add(Calendar.DAY_OF_MONTH, -k);
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
}