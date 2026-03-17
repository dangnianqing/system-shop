# EasyExcel 工具类使用说明

## 功能特性

- ✅ 单Sheet页Excel导出
- ✅ 多Sheet页Excel导出
- ✅ 单Sheet页Excel导入
- ✅ 指定Sheet页Excel导入
- ✅ 多Sheet页Excel导入
- ✅ 所有Sheet页Excel导入
- ✅ 自定义Sheet页配置
- ✅ 支持多种数据类型
- ✅ **美化样式支持**（多种风格）
- ✅ **自动列宽调整**
- ✅ **边框和颜色设置**
- ✅ **字体样式定制**

## 依赖配置

在 `pom.xml` 中添加 EasyExcel 依赖：

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.3.2</version>
</dependency>
```

## 实体类配置

创建Excel导入导出的实体类，使用 `@ExcelProperty` 注解：

```java
@Data
public class UserExcel {
    
    @ExcelProperty("用户ID")
    private Long id;
    
    @ExcelProperty("用户名")
    private String userName;
    
    @ExcelProperty("手机号")
    private String mobile;
    
    @ExcelProperty("状态")
    private Integer status;
    
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
    
    @ExcelProperty("金额")
    private BigDecimal amount;
}
```

## API 接口

### 1. 导出单Sheet页Excel（默认美化样式）
```
GET /api/excel/export/single
```

### 2. 导出单Sheet页Excel（商务风格）
```
GET /api/excel/export/single/business
```

### 3. 导出单Sheet页Excel（简约风格）
```
GET /api/excel/export/single/simple
```

### 4. 导出多Sheet页Excel（默认美化样式）
```
GET /api/excel/export/multi
```

### 5. 导出多Sheet页Excel（商务风格）
```
GET /api/excel/export/multi/business
```

### 6. 导出多Sheet页Excel（简约风格）
```
GET /api/excel/export/multi/simple
```

### 7. 导入Excel - 单Sheet页
```
POST /api/excel/import/single
Content-Type: multipart/form-data
Body: file (Excel文件)
```

### 4. 导入Excel - 指定Sheet页
```
POST /api/excel/import/sheet?sheetNo=0
Content-Type: multipart/form-data
Body: file (Excel文件)
```

### 5. 导入Excel - 所有Sheet页
```
POST /api/excel/import/all
Content-Type: multipart/form-data
Body: file (Excel文件)
```

## 使用示例

### 1. 单Sheet页导出（默认美化样式）

```java
@GetMapping("/export/single")
public void exportSingleSheet(HttpServletResponse response) {
    List<UserExcel> data = generateSampleData();
    ExcelUtils.exportExcel(response, "用户数据", "用户信息", data, UserExcel.class);
}
```

### 2. 单Sheet页导出（商务风格）

```java
@GetMapping("/export/single/business")
public void exportSingleSheetBusiness(HttpServletResponse response) {
    List<UserExcel> data = generateSampleData();
    ExcelUtils.exportExcelWithStyle(response, "用户数据_商务版", "用户信息", data, UserExcel.class, 
                                  new ExcelUtils.BusinessStyleStrategy());
}
```

### 3. 单Sheet页导出（简约风格）

```java
@GetMapping("/export/single/simple")
public void exportSingleSheetSimple(HttpServletResponse response) {
    List<UserExcel> data = generateSampleData();
    ExcelUtils.exportExcelWithStyle(response, "用户数据_简约版", "用户信息", data, UserExcel.class, 
                                  new ExcelUtils.SimpleStyleStrategy());
}
```

### 4. 多Sheet页导出（默认美化样式）

```java
@GetMapping("/export/multi")
public void exportMultiSheet(HttpServletResponse response) {
    List<UserExcel> data1 = generateSampleData();
    List<UserExcel> data2 = generateSampleData2();
    
    List<ExcelUtils.SheetConfig<UserExcel>> sheetConfigs = new ArrayList<>();
    sheetConfigs.add(ExcelUtils.createSheetConfig("用户信息", UserExcel.class, data1));
    sheetConfigs.add(ExcelUtils.createSheetConfig("用户统计", UserExcel.class, data2));
    
    ExcelUtils.exportMultiSheetExcel(response, "多Sheet用户数据", sheetConfigs);
}
```

### 5. 多Sheet页导出（商务风格）

```java
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
```

### 6. 多Sheet页导出（简约风格）

```java
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
```

### 3. 单Sheet页导入

```java
@PostMapping("/import/single")
public Result<List<UserExcel>> importSingleSheet(@RequestParam("file") MultipartFile file) {
    List<UserExcel> data = ExcelUtils.readExcel(file, UserExcel.class);
    return Result.success(data);
}
```

### 4. 指定Sheet页导入

```java
@PostMapping("/import/sheet")
public Result<List<UserExcel>> importSpecificSheet(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("sheetNo") int sheetNo) {
    List<UserExcel> data = ExcelUtils.readExcel(file, UserExcel.class, sheetNo);
    return Result.success(data);
}
```

### 5. 所有Sheet页导入

```java
@PostMapping("/import/all")
public Result<Map<String, List<UserExcel>>> importAllSheets(@RequestParam("file") MultipartFile file) {
    Map<String, List<UserExcel>> data = ExcelUtils.readAllSheets(file, UserExcel.class);
    return Result.success(data);
}
```

## 工具类方法说明

### ExcelUtils 主要方法

1. **exportExcel** - 导出单Sheet页Excel（默认美化样式）
   - 参数：response, fileName, sheetName, data, clazz

2. **exportExcelWithStyle** - 导出单Sheet页Excel（自定义样式）
   - 参数：response, fileName, sheetName, data, clazz, styleStrategy

3. **exportMultiSheetExcel** - 导出多Sheet页Excel（默认美化样式）
   - 参数：response, fileName, sheetConfigs

4. **exportMultiSheetExcelWithStyle** - 导出多Sheet页Excel（自定义样式）
   - 参数：response, fileName, sheetConfigs, styleStrategy

5. **readExcel** - 读取Excel（默认第一个Sheet页）
   - 参数：file, clazz
   - 返回：List<T>

6. **readExcel** - 读取指定Sheet页
   - 参数：file, clazz, sheetNo
   - 返回：List<T>

7. **readMultiSheetExcel** - 读取多Sheet页
   - 参数：file, sheetConfigs
   - 返回：Map<String, List<T>>

8. **readAllSheets** - 读取所有Sheet页
   - 参数：file, clazz
   - 返回：Map<String, List<T>>

### 样式策略类

1. **BeautifulStyleStrategy** - 默认美化样式
   - 表头：蓝色背景，白色字体，粗体
   - 数据行：隔行变色，居中对齐
   - 边框：细线边框

2. **BusinessStyleStrategy** - 商务风格
   - 表头：深蓝色背景，白色字体，粗体
   - 数据行：浅灰色背景
   - 边框：粗线边框

3. **SimpleStyleStrategy** - 简约风格
   - 表头：浅蓝色背景，黑色字体
   - 数据行：白色背景
   - 边框：细线边框

### SheetConfig 配置类

```java
public static class SheetConfig<T> {
    private String sheetName;    // Sheet页名称
    private Class<T> clazz;      // 实体类
    private List<T> data;        // 数据列表
}
```

## 注意事项

1. **实体类注解**：必须使用 `@ExcelProperty` 注解指定列名
2. **数据类型**：支持基本数据类型、BigDecimal、LocalDateTime等
3. **文件格式**：支持 .xlsx 和 .xls 格式
4. **编码问题**：文件名会自动进行URL编码处理
5. **异常处理**：工具类内部已处理常见异常

## 扩展功能

### 自定义日期格式

```java
@ExcelProperty(value = "创建时间", converter = LocalDateTimeStringConverter.class)
private LocalDateTime createTime;
```

### 自定义数字格式

```java
@ExcelProperty(value = "金额", converter = BigDecimalStringConverter.class)
private BigDecimal amount;
```

### 忽略某些字段

```java
@ExcelIgnore
private String ignoreField;
```

## 性能优化建议

1. **大数据量处理**：使用流式读写，避免内存溢出
2. **并发处理**：多线程处理大量Excel文件
3. **缓存机制**：对重复的Excel模板进行缓存
4. **分批处理**：将大量数据分批导入导出

## 常见问题

### Q: 如何处理Excel中的空值？
A: EasyExcel会自动处理空值，实体类中使用包装类型（如Integer而不是int）

### Q: 如何设置Excel列宽？
A: 可以使用 `@ContentStyle` 注解设置列宽

### Q: 如何处理Excel中的公式？
A: EasyExcel默认读取公式的计算结果，如需读取公式本身需要特殊配置

### Q: 如何设置Excel样式？
A: 可以使用 `@HeadStyle` 和 `@ContentStyle` 注解设置样式 