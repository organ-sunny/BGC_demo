package com.sunny.util;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel
 *
 * @author ZJ
 */
public final class ExcelUtil {

    /**
     * 数据集合
     */
    private final List<List<String>> data = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param path       路径
     * @param sheetIndex sheet序号
     */
    public ExcelUtil(String path, int sheetIndex) {
        FileInputStream fileInputStream = null;
        Workbook workbook = null;
        try {
            // 日期格式
            String dateForMat = "yyyy-MM-dd HH:mm:ss";
            fileInputStream = new FileInputStream(path);
            if (path.endsWith(".xls")) {
                workbook = WorkbookFactory.create(fileInputStream);
            } else if (path.endsWith(".xlsx")) {
                workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(fileInputStream);
            } else {
                throw new RuntimeException("文件错误");
            }
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int index = 0;
            Integer titleSize = null;
            for (Row row : sheet) {
                index++;
                List<String> strings = new ArrayList<>();
                if (index == 1) {
                    for (Cell cell : row) {
                        String cellData = cell.getStringCellValue();
                        if (isEmpty(cellData)) {
                            break;
                        }
                        strings.add(cellData);
                    }
                    titleSize = strings.size();
                } else {
                    if (titleSize != null) {
                        for (int i = 0; i < titleSize; i++) {
                            Cell cell = row.getCell(i);
                            String cellData = "";
                            if (cell != null) {
                                String c;
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                        c = getDate(cell.getDateCellValue(), dateForMat);
                                    } else {
                                        c = doubleToString(cell.getNumericCellValue());
                                    }
                                } else {
                                    c = cell.getStringCellValue();
                                }
                                if (c != null) {
                                    cellData = c;
                                }
                            }
                            strings.add(cellData);
                        }
                    }
                }
                this.data.add(strings);
            }
        } catch (IOException | org.apache.poi.EmptyFileException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断是否为null
     *
     * @param o 数据
     * @return 是null返回true
     */
    private static boolean isEmpty(Object o) {
        return o == null || "".equals(o.toString());
    }

    /**
     * 获取指定日期格式
     *
     * @param format 格式
     * @return 指定格式的日期
     */
    private static String getDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 将double转成string
     *
     * @param d d
     * @return string
     */
    private static String doubleToString(Double d) {
        return new BigDecimal(d).toPlainString();
    }

    /**
     * 获取所有数据装配成map
     *
     * @return 所有数据
     */
    public List<Map<String, Object>> getAllDataOfMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<String> keys = this.data.get(0);
        int size = keys.size();
        for (List<String> list : this.data) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < size; i++) {
                map.put(keys.get(i), list.get(i));
            }
            result.add(map);
        }
        result.remove(0);
        return result;
    }

    public List<List<String>> getAllData() {
        return this.data;
    }

}