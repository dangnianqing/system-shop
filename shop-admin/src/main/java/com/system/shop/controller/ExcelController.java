package com.system.shop.controller;

import com.system.shop.common.Result;
import com.system.shop.entity.excel.UserExcel;
import com.system.shop.utils.ExcelUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel导入导出控制器
 */
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    /**
     * 导出单Sheet页Excel（默认美化样式）
     */
    @GetMapping("/export/single")
    public void exportSingleSheet(HttpServletResponse response) {
        List<UserExcel> data = generateSampleData();
        ExcelUtils.exportExcel(response, "用户数据", "用户信息", data, UserExcel.class);
    }

    /**
     * 导出单Sheet页Excel（商务风格）
     */
    @GetMapping("/export/single/business")
    public void exportSingleSheetBusiness(HttpServletResponse response) {
        List<UserExcel> data = generateSampleData();
        ExcelUtils.exportExcelWithStyle(response, "用户数据_商务版", "用户信息", data, UserExcel.class, 
                                      new ExcelUtils.BusinessStyleStrategy());
    }

    /**
     * 导出单Sheet页Excel（简约风格）
     */
    @GetMapping("/export/single/simple")
    public void exportSingleSheetSimple(HttpServletResponse response) {
        List<UserExcel> data = generateSampleData();
        ExcelUtils.exportExcelWithStyle(response, "用户数据_简约版", "用户信息", data, UserExcel.class, 
                                      new ExcelUtils.SimpleStyleStrategy());
    }

    /**
     * 导出多Sheet页Excel（默认美化样式）
     */
    @GetMapping("/export/multi")
    public void exportMultiSheet(HttpServletResponse response) {
        List<UserExcel> data1 = generateSampleData();
        List<UserExcel> data2 = generateSampleData2();
        
        List<ExcelUtils.SheetConfig<UserExcel>> sheetConfigs = new ArrayList<>();
        sheetConfigs.add(ExcelUtils.createSheetConfig("用户信息", UserExcel.class, data1));
        sheetConfigs.add(ExcelUtils.createSheetConfig("用户统计", UserExcel.class, data2));
        
        ExcelUtils.exportMultiSheetExcel(response, "多Sheet用户数据", sheetConfigs);
    }

    /**
     * 导出多Sheet页Excel（商务风格）
     */
    @GetMapping("/export/multi/business")
    public void exportMultiSheetBusiness(HttpServletResponse response) {
        List<UserExcel> data1 = generateSampleData();
        List<UserExcel> data2 = generateSampleData2();
        
        List<ExcelUtils.SheetConfig<UserExcel>> sheetConfigs = new ArrayList<>();
        sheetConfigs.add(ExcelUtils.createSheetConfig("用户信息", UserExcel.class, data1));
        sheetConfigs.add(ExcelUtils.createSheetConfig("用户统计", UserExcel.class, data2));
        
        ExcelUtils.exportMultiSheetExcelWithStyle(response, "多Sheet用户数据_商务版", sheetConfigs, 
                                                new ExcelUtils.BusinessStyleStrategy());
    }

    /**
     * 导出多Sheet页Excel（简约风格）
     */
    @GetMapping("/export/multi/simple")
    public void exportMultiSheetSimple(HttpServletResponse response) {
        List<UserExcel> data1 = generateSampleData();
        List<UserExcel> data2 = generateSampleData2();
        
        List<ExcelUtils.SheetConfig<UserExcel>> sheetConfigs = new ArrayList<>();
        sheetConfigs.add(ExcelUtils.createSheetConfig("用户信息", UserExcel.class, data1));
        sheetConfigs.add(ExcelUtils.createSheetConfig("用户统计", UserExcel.class, data2));
        
        ExcelUtils.exportMultiSheetExcelWithStyle(response, "多Sheet用户数据_简约版", sheetConfigs, 
                                                new ExcelUtils.SimpleStyleStrategy());
    }

    /**
     * 导入Excel - 单Sheet页
     */
    @PostMapping("/import/single")
    public Result<List<UserExcel>> importSingleSheet(@RequestParam("file") MultipartFile file) {
        List<UserExcel> data = ExcelUtils.readExcel(file, UserExcel.class);
        return Result.success(data);
    }

    /**
     * 导入Excel - 指定Sheet页
     */
    @PostMapping("/import/sheet")
    public Result<List<UserExcel>> importSpecificSheet(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("sheetNo") int sheetNo) {
        List<UserExcel> data = ExcelUtils.readExcel(file, UserExcel.class, sheetNo);
        return Result.success(data);
    }

    /**
     * 导入Excel - 所有Sheet页
     */
    @PostMapping("/import/all")
    public Result<Map<String, List<UserExcel>>> importAllSheets(@RequestParam("file") MultipartFile file) {
        Map<String, List<UserExcel>> data = ExcelUtils.readAllSheets(file, UserExcel.class);
        return Result.success(data);
    }

    /**
     * 生成示例数据1
     */
    private List<UserExcel> generateSampleData() {
        List<UserExcel> data = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            UserExcel user = new UserExcel();
            user.setId((long) i);
            user.setUserName("用户" + i);
            user.setMobile("1380000" + String.format("%04d", i));
            user.setStatus(i % 2);
            user.setCreateTime(LocalDateTime.now());
            user.setAmount(new BigDecimal("100.50").multiply(new BigDecimal(i)));
            data.add(user);
        }
        return data;
    }

    /**
     * 生成示例数据2
     */
    private List<UserExcel> generateSampleData2() {
        List<UserExcel> data = new ArrayList<>();
        for (int i = 11; i <= 20; i++) {
            UserExcel user = new UserExcel();
            user.setId((long) i);
            user.setUserName("测试用户" + i);
            user.setMobile("1390000" + String.format("%04d", i));
            user.setStatus(1);
            user.setCreateTime(LocalDateTime.now().minusDays(i));
            user.setAmount(new BigDecimal("200.00").multiply(new BigDecimal(i)));
            data.add(user);
        }
        return data;
    }
} 