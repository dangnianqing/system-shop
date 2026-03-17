package com.system.shop.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Excel工具类 - 基于EasyExcel
 * 支持多Sheet页导入导出，带美化样式
 */
@Slf4j
public class ExcelUtils {

    /**
     * 导出Excel - 单Sheet页（美化版）
     *
     * @param response   响应对象
     * @param fileName   文件名
     * @param sheetName  Sheet页名称
     * @param data       数据列表
     * @param clazz      实体类
     * @param <T>        泛型
     */
    public static <T> void exportExcel(HttpServletResponse response, String fileName, String sheetName,
                                      List<T> data, Class<T> clazz) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName + ".xlsx");

            EasyExcel.write(response.getOutputStream(), clazz)
                    .registerWriteHandler(new BeautifulStyleStrategy())
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet(sheetName)
                    .doWrite(data);
        } catch (IOException e) {
            log.error("导出Excel失败", e);
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    /**
     * 导出Excel - 多Sheet页（美化版）
     *
     * @param response    响应对象
     * @param fileName    文件名
     * @param sheetConfigs Sheet页配置列表
     * @param <T>         泛型
     */
    public static <T> void exportMultiSheetExcel(HttpServletResponse response, String fileName,
                                                List<SheetConfig<T>> sheetConfigs) {
        exportMultiSheetExcelWithStyle(response, fileName, sheetConfigs, new BeautifulStyleStrategy());
    }

    /**
     * 导出Excel - 多Sheet页（自定义样式）
     *
     * @param response    响应对象
     * @param fileName    文件名
     * @param sheetConfigs Sheet页配置列表
     * @param styleStrategy 样式策略
     * @param <T>         泛型
     */
    public static <T> void exportMultiSheetExcelWithStyle(HttpServletResponse response, String fileName,
                                                         List<SheetConfig<T>> sheetConfigs, 
                                                         RowWriteHandler styleStrategy) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName + ".xlsx");

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .build();
            try {
                for (int i = 0; i < sheetConfigs.size(); i++) {
                    SheetConfig<T> config = sheetConfigs.get(i);
                    WriteSheet writeSheet = EasyExcel.writerSheet(i, config.getSheetName())
                            .head(config.getClazz())
                            .build();
                    excelWriter.write(config.getData(), writeSheet);
                }
            } finally {
                excelWriter.finish();
            }
        } catch (IOException e) {
            log.error("导出多Sheet Excel失败", e);
            throw new RuntimeException("导出多Sheet Excel失败", e);
        }
    }

    /**
     * 导出Excel - 带自定义样式
     *
     * @param response   响应对象
     * @param fileName   文件名
     * @param sheetName  Sheet页名称
     * @param data       数据列表
     * @param clazz      实体类
     * @param styleStrategy 样式策略
     * @param <T>        泛型
     */
    public static <T> void exportExcelWithStyle(HttpServletResponse response, String fileName, String sheetName,
                                               List<T> data, Class<T> clazz, RowWriteHandler styleStrategy) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName + ".xlsx");

            EasyExcel.write(response.getOutputStream(), clazz)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet(sheetName)
                    .doWrite(data);
        } catch (IOException e) {
            log.error("导出Excel失败", e);
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    /**
     * 导出单列字符串List，支持自定义表头
     * @param response  HttpServletResponse
     * @param fileName  文件名
     * @param header    表头（如："手机号"）
     * @param data      数据
     */
    public static void exportSingleColumn(HttpServletResponse response, String fileName, String header, List<String> data) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName + ".xlsx");

            // 构造表头
            List<List<String>> head = Collections.singletonList(Collections.singletonList(header));
            // 构造数据
            List<List<String>> dataList = data.stream()
                    .map(Collections::singletonList)
                    .collect(java.util.stream.Collectors.toList());

            EasyExcel.write(response.getOutputStream())
                    .head(head)
                    .sheet("Sheet1")
                    .doWrite(dataList);
        } catch (Exception e) {
            log.error("导出Excel失败", e);
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    /**
     * 读取Excel - 单Sheet页
     *
     * @param file  上传的文件
     * @param clazz 实体类
     * @param <T>   泛型
     * @return 数据列表
     */
    public static <T> List<T> readExcel(MultipartFile file, Class<T> clazz) {
        return readExcel(file, clazz, 0);
    }

    /**
     * 读取Excel - 指定Sheet页
     *
     * @param file     上传的文件
     * @param clazz    实体类
     * @param sheetNo  Sheet页索引（从0开始）
     * @param <T>      泛型
     * @return 数据列表
     */
    public static <T> List<T> readExcel(MultipartFile file, Class<T> clazz, int sheetNo) {
        try {
            List<T> dataList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), clazz, new AnalysisEventListener<T>() {
                @Override
                public void invoke(T data, AnalysisContext context) {
                    dataList.add(data);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    log.info("Excel读取完成，共读取{}条数据", dataList.size());
                }
            }).sheet(sheetNo).doRead();
            return dataList;
        } catch (IOException e) {
            log.error("读取Excel失败", e);
            throw new RuntimeException("读取Excel失败", e);
        }
    }

    /**
     * 读取Excel - 多Sheet页
     *
     * @param file       上传的文件
     * @param sheetConfigs Sheet页配置
     * @param <T>         泛型
     * @return 多Sheet页数据
     */
    public static <T> Map<String, List<T>> readMultiSheetExcel(MultipartFile file,
                                                               List<SheetConfig<T>> sheetConfigs) {
        try {
            Map<String, List<T>> result = new java.util.HashMap<>();
            InputStream inputStream = file.getInputStream();

            for (SheetConfig<T> config : sheetConfigs) {
                List<T> dataList = new ArrayList<>();
                EasyExcel.read(inputStream, config.getClazz(), new AnalysisEventListener<T>() {
                    @Override
                    public void invoke(T data, AnalysisContext context) {
                        dataList.add(data);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        log.info("Sheet页[{}]读取完成，共读取{}条数据", config.getSheetName(), dataList.size());
                    }
                }).sheet(config.getSheetName()).doRead();
                result.put(config.getSheetName(), dataList);
            }
            return result;
        } catch (IOException e) {
            log.error("读取多Sheet Excel失败", e);
            throw new RuntimeException("读取多Sheet Excel失败", e);
        }
    }

    /**
     * 读取Excel - 所有Sheet页
     *
     * @param file  上传的文件
     * @param clazz 实体类
     * @param <T>   泛型
     * @return 所有Sheet页数据
     */
    public static <T> Map<String, List<T>> readAllSheets(MultipartFile file, Class<T> clazz) {
        try {
            Map<String, List<T>> result = new java.util.HashMap<>();
            
            // 获取所有Sheet页信息
            List<ReadSheet> readSheets = EasyExcel.read(file.getInputStream()).build().excelExecutor().sheetList();
            
            for (ReadSheet readSheet : readSheets) {
                String sheetName = readSheet.getSheetName();
                List<T> dataList = new ArrayList<>();
                
                // 为每个Sheet页重新创建InputStream
                try (InputStream inputStream = file.getInputStream()) {
                    EasyExcel.read(inputStream, clazz, new AnalysisEventListener<T>() {
                        @Override
                        public void invoke(T data, AnalysisContext context) {
                            dataList.add(data);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                            log.info("Sheet页[{}]读取完成，共读取{}条数据", sheetName, dataList.size());
                        }
                    }).sheet(sheetName).doRead();
                }
                result.put(sheetName, dataList);
            }
            return result;
        } catch (IOException e) {
            log.error("读取所有Sheet页失败", e);
            throw new RuntimeException("读取所有Sheet页失败", e);
        }
    }

    /**
     * 美化样式策略类
     */
    public static class BeautifulStyleStrategy implements RowWriteHandler {
        
        @Override
        public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                   Row row, Integer relativeRowIndex, Boolean isHead) {
            
            Sheet sheet = writeSheetHolder.getSheet();
            Workbook workbook = sheet.getWorkbook();
            
            // 设置行高
            if (isHead) {
                row.setHeight((short) 500); // 表头行高
            } else {
                row.setHeight((short) 400); // 数据行高
            }
            
            // 为每个单元格设置样式
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    CellStyle cellStyle = workbook.createCellStyle();
                    
                    // 设置边框
                    cellStyle.setBorderTop(BorderStyle.THIN);
                    cellStyle.setBorderBottom(BorderStyle.THIN);
                    cellStyle.setBorderLeft(BorderStyle.THIN);
                    cellStyle.setBorderRight(BorderStyle.THIN);
                    
                    // 设置对齐方式
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    
                    // 设置字体
                    Font font = workbook.createFont();
                    if (isHead) {
                        // 表头样式
                        font.setBold(true);
                        font.setFontHeightInPoints((short) 12);
                        font.setColor(IndexedColors.WHITE.getIndex());
                        cellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    } else {
                        // 数据行样式
                        font.setFontHeightInPoints((short) 11);
                        font.setColor(IndexedColors.BLACK.getIndex());
                        
                        // 隔行变色
                        if (relativeRowIndex % 2 == 0) {
                            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        } else {
                            cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        }
                    }
                    
                    cellStyle.setFont(font);
                    cell.setCellStyle(cellStyle);
                }
            }
        }
    }

    /**
     * 商务风格样式策略
     */
    public static class BusinessStyleStrategy implements RowWriteHandler {
        
        @Override
        public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                   Row row, Integer relativeRowIndex, Boolean isHead) {
            
            Sheet sheet = writeSheetHolder.getSheet();
            Workbook workbook = sheet.getWorkbook();
            
            // 设置行高
            if (isHead) {
                row.setHeight((short) 450);
            } else {
                row.setHeight((short) 350);
            }
            
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    CellStyle cellStyle = workbook.createCellStyle();
                    
                    // 设置边框
                    cellStyle.setBorderTop(BorderStyle.MEDIUM);
                    cellStyle.setBorderBottom(BorderStyle.MEDIUM);
                    cellStyle.setBorderLeft(BorderStyle.MEDIUM);
                    cellStyle.setBorderRight(BorderStyle.MEDIUM);
                    
                    // 设置对齐方式
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    
                    Font font = workbook.createFont();
                    if (isHead) {
                        // 表头样式 - 深蓝色背景
                        font.setBold(true);
                        font.setFontHeightInPoints((short) 12);
                        font.setColor(IndexedColors.WHITE.getIndex());
                        cellStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    } else {
                        // 数据行样式
                        font.setFontHeightInPoints((short) 10);
                        font.setColor(IndexedColors.BLACK.getIndex());
                        
                        // 浅灰色背景
                        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    }
                    
                    cellStyle.setFont(font);
                    cell.setCellStyle(cellStyle);
                }
            }
        }
    }

    /**
     * 简约风格样式策略
     */
    public static class SimpleStyleStrategy implements RowWriteHandler {
        
        @Override
        public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                   Row row, Integer relativeRowIndex, Boolean isHead) {
            
            Sheet sheet = writeSheetHolder.getSheet();
            Workbook workbook = sheet.getWorkbook();
            
            row.setHeight((short) 400);
            
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    CellStyle cellStyle = workbook.createCellStyle();
                    
                    // 设置边框
                    cellStyle.setBorderTop(BorderStyle.THIN);
                    cellStyle.setBorderBottom(BorderStyle.THIN);
                    cellStyle.setBorderLeft(BorderStyle.THIN);
                    cellStyle.setBorderRight(BorderStyle.THIN);
                    
                    // 设置对齐方式
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    
                    Font font = workbook.createFont();
                    if (isHead) {
                        // 表头样式 - 浅蓝色背景
                        font.setBold(true);
                        font.setFontHeightInPoints((short) 11);
                        font.setColor(IndexedColors.BLACK.getIndex());
                        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    } else {
                        // 数据行样式
                        font.setFontHeightInPoints((short) 10);
                        font.setColor(IndexedColors.BLACK.getIndex());
                    }
                    
                    cellStyle.setFont(font);
                    cell.setCellStyle(cellStyle);
                }
            }
        }
    }

    /**
     * Sheet页配置类
     *
     * @param <T> 泛型
     */
    public static class SheetConfig<T> {
        private String sheetName;
        private Class<T> clazz;
        private List<T> data;

        public SheetConfig(String sheetName, Class<T> clazz, List<T> data) {
            this.sheetName = sheetName;
            this.clazz = clazz;
            this.data = data;
        }

        public String getSheetName() {
            return sheetName;
        }

        public Class<T> getClazz() {
            return clazz;
        }

        public List<T> getData() {
            return data;
        }
    }

    /**
     * 创建Sheet配置
     *
     * @param sheetName Sheet页名称
     * @param clazz     实体类
     * @param data      数据列表
     * @param <T>       泛型
     * @return Sheet配置
     */
    public static <T> SheetConfig<T> createSheetConfig(String sheetName, Class<T> clazz, List<T> data) {
        return new SheetConfig<>(sheetName, clazz, data);
    }
} 