package com.hwsafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.hwsafe.common.ColumnMerge;
import com.hwsafe.common.RowMerge;
import com.hwsafe.validate.ValidationBean;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * ExcelUtils导出数据
 *
 * @author
 */
public class ExcelUtil {

    private static final int MAX_ROWS = 60000;

    /**
     * 导入excel的错误信息
     */
    public static final String EXCEL_ERRORMSG = "excel_error";
    /**
     * excle导入数据库要转换的值
     */
    public static final String ID_VALUE = "id_value";

    public static void main(String[] args) throws Exception {
        /*
         * String[][] str = { { "class", "班级" }, { "name", "姓名" }, { "name1",
         * "姓名" } }; List<Map<String, Object>> list = new ArrayList<Map<String,
         * Object>>(); Map<String, Object> map = new HashMap<String, Object>();
         * map.put("class", "班级1"); map.put("name", "张四"); map.put("name1",
         * "张四"); list.add(map); map = new HashMap<String, Object>();
         * map.put("class", "班级1"); map.put("name", "张三"); map.put("name1",
         * "张三3"); list.add(map); map = new HashMap<String, Object>();
         * map.put("class", "班级2"); map.put("name", "张五3"); map.put("name1",
         * "张五"); list.add(map); map = new HashMap<String, Object>();
         * map.put("class", "班级1"); map.put("name", "张六"); map.put("name1",
         * "张六"); list.add(map); try { FileOutputStream fileOutputStream = new
         * FileOutputStream("D:\\genter/test.xls"); baseexportRow("合并", str,
         * null, null, list, fileOutputStream); } catch (FileNotFoundException
         * e) { e.printStackTrace(); }
         */
        long cuurent = System.currentTimeMillis();
        File file = new File("D:\\ee.xls");
        List<Map<String, Object>> list = readExcel(file, 0, 1);
        // System.err.println(list.size());
        System.err.println(list.size() + JSONObject.toJSONString(list));
        // System.out.println((System.currentTimeMillis()-cuurent)/1000);

    }

    // ************************导入开始*********************************//

    /**
     * 读取excel数据,调用这方法开始
     *
     * @param file
     *            上传文件
     * @param indexNum
     *            至少需要多少列数据
     * @param sheetIndex
     *            sheet页下标：从0开始
     * @param startReadLine
     *            开始读取的行:从0开始
     * @param tailLine
     *            去除最后读取的行 注意：这导入功能也适用于单行读取， 直接调用 readExcelToObj() 方法即可；
     *            参数1：传入excel文件的输入流； 参数2：指定你希望至少要读入多少列数据（比如传入个0，
     *            就代表：如果你有的行只有3列数据的话，那么获得的数组长度就只有3；
     *            如果你传入了10，那些只有3列的数据会自动填充空字符串给数组，使每个数组最小长度为10）；
     */
    public static List<Object[]> readExcelToObj(File file, int indexNum,
            int sheetIndex, int startReadLine, int tailLine) throws Exception {
        Workbook wb = WorkbookFactory.create(file);
        List<Object[]> objArrList = new ArrayList<>();
        readExcel(wb, sheetIndex, startReadLine, tailLine, objArrList,
                indexNum);
        return objArrList;
    }

    /**
     * 读取excel文件
     *
     * @param wb
     * @param sheetIndex
     *            sheet页下标：从0开始
     * @param startReadLine
     *            开始读取的行:从0开始
     * @param tailLine
     *            去除最后读取的行
     */
    public static void readExcel(Workbook wb, int sheetIndex, int startReadLine,
            int tailLine, List<Object[]> objArrList, int indexNum) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;

        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine
                + 1; i++) {
            row = sheet.getRow(i);
            List<Object> objList = new ArrayList<>();
            if (row != null) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell c = row.getCell(j);
                    if (c == null) {
                        objList.add("");
                        continue;
                    }
                    boolean isMerge = isMergedRegion(sheet, i,
                            c.getColumnIndex());
                    // 判断是否具有合并单元格
                    if (isMerge) {
                        String rs = getMergedRegionValue(sheet, row.getRowNum(),
                                c.getColumnIndex());
                        objList.add(rs);
                    } else {
                        objList.add(getCellValue(c));
                    }

                }
            }
            while (objList.size() < indexNum) {
                objList.add("");
            }
            objArrList.add(objList.toArray());
        }
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet, int row,
            int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {

                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }

        return null;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row
     *            行下标
     * @param column
     *            列下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {

        if (cell == null)
            return "";

        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

            return cell.getStringCellValue();

        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

            return String.valueOf(cell.getBooleanCellValue());

        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

            return cell.getCellFormula();

        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                String value = DateFormatUtils.format(date, "yyyy-MM-dd");
                return value;
            } else {
                Object value = cell.getNumericCellValue();
                DecimalFormat df = new DecimalFormat("0");
                return df.format(value);
            }
        }

        return "";
    }

    // ************************导入结束*********************************//

    /**
     * 导出数据至Excel文件
     *
     * @param colsNames
     *            列名
     * @param data
     *            数据源
     * @param OutputStream
     *            输出流
     * @param filename
     *            excel文件名称
     * @param sheetname
     *            excel 工作区名称
     * @param title
     *            数据内容标题
     * @param foot
     *            数据内容底部
     * @param colmerge
     *            需要合并的列
     * @param condition
     *            指定以红色显示的内容
     * @author spring
     */
    public static void exportDataToExcel(List<String> colsNames,
            List<List<String>> data, OutputStream os, String filename,
            String sheetname, String title, String foot, String colmerge,
            String condition) {

        if (filename == null)
            filename = "Unidentified.xls";
        if (sheetname == null || sheetname.equals("")) {
            sheetname = "Sheet";
        }
        int width = 0;// excel总列合并宽度

        // 解析condition对应的列、值

        String[] conditions = new String[0];
        if (condition != null) {
            conditions = condition.split(",");
        }

        WritableWorkbook workbook = null;
        try {
            workbook = null;
//            workbook = Workbook.createWorkbook(os);
            if (data == null || data.size() < 1) {// 没有数据时
                WritableSheet sheet = workbook.createSheet(sheetname, 0);

                // 设置字体样式
                WritableFont font = new WritableFont(WritableFont.ARIAL, 24);
                // 设置一般样式
                WritableCellFormat cf = new WritableCellFormat(font);
                cf.setAlignment(Alignment.CENTRE);// 设置居中
                cf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
                cf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线

                int j = 0;// 行号标识
                width = colsNames.size();
                // 设置标题
                if (title != null && j == 0) {
                    font.setBoldStyle(WritableFont.BOLD);
                    Label header = new Label(0, j, title, cf);
                    sheet.mergeCells(0, j, width - 1, j);// 合并单元格
                    sheet.addCell(header);
                    j++;
                }

                // 设置列名
                WritableFont colsFont = new WritableFont(WritableFont.ARIAL);
                WritableCellFormat colscf = new WritableCellFormat(colsFont);
                colscf.setAlignment(Alignment.CENTRE);// 设置居中
                colscf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
                colscf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线
                for (int i = 0; i < colsNames.size(); i++) {
                    // 设置指定内容的颜色 condition (e.g. key=value)
                    for (String string : conditions) {
                        int index = string.indexOf("=");
                        int column = Integer
                                .parseInt(string.substring(0, index));
                        String value = string.substring(index + 1);
                        if (column == i && colsNames.get(i).equals(value)) {
                            colsFont.setColour(Colour.RED);
                            break;
                        }
                    }

                    // 合并指定值相同的列
                    mergeColumns(sheet, j, colsNames, i, colmerge);

                    Label cell = new Label(i, j, colsNames.get(i), colscf);
                    sheet.addCell(cell);
                    sheet.setColumnView(i, colsNames.get(i).getBytes().length);
                }
                j++;

                // 设置底部
                if (foot != null) {
                    Label footer = new Label(0, j, foot, cf);
                    sheet.mergeCells(0, j, width - 1, j);// 合并单元格
                    sheet.addCell(footer);
                }
            } else {
                if (data.size() > 0) {
                    int index = 1;
                    for (int i = 0; i < data.size(); i++) {
                        int row_to = data.size() - i > MAX_ROWS ? i + MAX_ROWS
                                : data.size();
                        writeSheet(colsNames, data.subList(i, row_to),
                                sheetname + index, title, foot, colmerge, width,
                                conditions, workbook);
                        index++;
                    }
                } else {
                    writeSheet(colsNames, data, sheetname, title, foot,
                            colmerge, width, conditions, workbook);
                }
            }

            workbook.write();
        } catch (WriteException e) {
        } catch (IOException e) {
        } finally {
            try {
                workbook.close();
            } catch (WriteException e) {
            } catch (IOException e) {
            }
        }

    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    private static void writeSheet(List<String> colsNames,
            List<List<String>> data, String sheetname, String title,
            String foot, String colmerge, int width, String[] conditions,
            WritableWorkbook workbook)
            throws WriteException, RowsExceededException {
        WritableSheet sheet = workbook.createSheet(sheetname, 0);

        // 设置字体样式
        WritableFont font = new WritableFont(WritableFont.ARIAL, 24);
        // 设置一般样式
        WritableCellFormat cf = new WritableCellFormat(font);
        cf.setAlignment(Alignment.CENTRE);// 设置居中
        cf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线

        int j = 0;// 行号标识
        width = colsNames.size();
        // 设置标题
        if (title != null && j == 0) {
            font.setBoldStyle(WritableFont.BOLD);
            Label header = new Label(0, j, title, cf);
            sheet.mergeCells(0, j, width - 1, j);// 合并单元格
            sheet.addCell(header);
            j++;
        }

        // 设置列名
        WritableFont colsFont = new WritableFont(WritableFont.ARIAL, 20);
        WritableCellFormat colscf = new WritableCellFormat(colsFont);
        colscf.setAlignment(Alignment.CENTRE);// 设置居中
        colscf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
        colscf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线
        for (int i = 0; i < colsNames.size(); i++) {
            // 设置指定内容的颜色 condition (e.g. key=value)
            for (String string : conditions) {
                int index = string.indexOf("=");
                int column = Integer.parseInt(string.substring(0, index));
                String value = string.substring(index + 1);
                if (column == i && colsNames.get(i).equals(value)) {
                    colsFont.setColour(Colour.RED);
                    break;
                }
            }

            // 合并指定值相同的列
            mergeColumns(sheet, j, colsNames, i, colmerge);

            Label cell = new Label(i, j, colsNames.get(i), colscf);
            sheet.addCell(cell);
            sheet.setColumnView(i, colsNames.get(i).getBytes().length);
        }
        j++;

        WritableFont bodyFont = new WritableFont(WritableFont.ARIAL);
        WritableCellFormat bodycf = new WritableCellFormat(bodyFont);
        bodycf.setAlignment(Alignment.CENTRE);// 设置居中
        bodycf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
        bodycf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线
        for (List<String> cells : data) {
            for (int i = 0; i < cells.size(); i++) {
                // 设置指定内容的颜色 condition (e.g. key=value)
                for (String string : conditions) {
                    int index = string.indexOf("=");
                    int column = Integer.parseInt(string.substring(0, index));
                    String value = string.substring(index + 1);
                    if (column == i && cells.get(i).equals(value)) {
                        bodyFont.setColour(Colour.RED);
                        break;
                    }
                }

                // 合并指定值相同的列
                mergeColumns(sheet, j, cells, i, colmerge);

                Label cell = new Label(i, j, cells.get(i), bodycf);
                sheet.addCell(cell);
            }
            j++;
        }

        // 设置底部
        if (foot != null) {
            Label footer = new Label(0, j, foot, cf);
            sheet.mergeCells(0, j, width - 1, j);// 合并单元格
            sheet.addCell(footer);
        }
    }

    /**
     * 导出数据至Excel文件
     *
     * @param colsNames
     *            列名
     * @param data
     *            数据源
     * @param OutputStream
     *            输出流
     * @param filename
     *            excel文件名称
     * @param sheetname
     *            excel 工作区名称
     * @param title
     *            数据内容标题
     * @param foot
     *            数据内容底部
     * @param colmerge
     *            需要合并的列
     * @param condition
     *            指定以红色显示的内容
     * @author spring
     */
    public static void exportDataToExcelImg(List<String> colsNames,
            OutputStream os, String filename, String sheetname, String title,
            String foot, String colmerge, String condition,
            Map<String, String> goodImgs,
            Map<String, List<List<String>>> goodCodeSize) {

        if (filename == null)
            filename = "Unidentified.xls";
        if (sheetname == null || sheetname.equals("")) {
            sheetname = "Sheet";
        }
        int width = 0;// excel总列合并宽度

        // 解析condition对应的列、值

        String[] conditions = new String[0];
        if (condition != null) {
            conditions = condition.split(",");
        }

        WritableWorkbook workbook = null;
        try {
            workbook = null;
//            workbook = Workbook.createWorkbook(os);
            if (goodImgs == null || goodImgs.size() < 1) {// 没有数据时
                WritableSheet sheet = workbook.createSheet(sheetname, 0);

                // 设置字体样式
                WritableFont font = new WritableFont(WritableFont.ARIAL, 24);
                // 设置一般样式
                WritableCellFormat cf = new WritableCellFormat(font);
                cf.setAlignment(Alignment.CENTRE);// 设置居中
                cf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
                cf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线

                int j = 0;// 行号标识
                width = colsNames.size();
                // 设置标题
                if (title != null && j == 0) {
                    font.setBoldStyle(WritableFont.BOLD);
                    Label header = new Label(0, j, title, cf);
                    sheet.mergeCells(0, j, width - 1, j);// 合并单元格
                    sheet.addCell(header);
                    j++;
                }

                // 设置列名
                WritableFont colsFont = new WritableFont(WritableFont.ARIAL);
                WritableCellFormat colscf = new WritableCellFormat(colsFont);
                colscf.setAlignment(Alignment.CENTRE);// 设置居中
                colscf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
                colscf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线
                for (int i = 0; i < colsNames.size(); i++) {
                    // 设置指定内容的颜色 condition (e.g. key=value)
                    for (String string : conditions) {
                        int index = string.indexOf("=");
                        int column = Integer
                                .parseInt(string.substring(0, index));
                        String value = string.substring(index + 1);
                        if (column == i && colsNames.get(i).equals(value)) {
                            colsFont.setColour(Colour.RED);
                            break;
                        }
                    }

                    // 合并指定值相同的列
                    mergeColumns(sheet, j, colsNames, i, colmerge);

                    Label cell = new Label(i, j, colsNames.get(i), colscf);
                    sheet.addCell(cell);
                    sheet.setColumnView(i, colsNames.get(i).getBytes().length);
                }
                j++;

                // 设置底部
                if (foot != null) {
                    Label footer = new Label(0, j, foot, cf);
                    sheet.mergeCells(0, j, width - 1, j);// 合并单元格
                    sheet.addCell(footer);
                }
            } else {

                writeSheetImg(colsNames, sheetname, title, foot, colmerge,
                        width, conditions, workbook, goodImgs, goodCodeSize);

            }

            workbook.write();
        } catch (WriteException e) {
        } catch (IOException e) {
        } finally {
            try {
                workbook.close();
            } catch (WriteException e) {
            } catch (IOException e) {
            }
        }

    }

    private static void writeSheetImg(List<String> colsNames, String sheetname,
            String title, String foot, String colmerge, int width,
            String[] conditions, WritableWorkbook workbook,
            Map<String, String> goodImgs,
            Map<String, List<List<String>>> goodCodeSize)
            throws WriteException, RowsExceededException {
        WritableSheet sheet = workbook.createSheet(sheetname, 0);

        // 设置字体样式
        WritableFont font = new WritableFont(WritableFont.ARIAL, 24);
        // 设置一般样式
        WritableCellFormat cf = new WritableCellFormat(font);
        cf.setAlignment(Alignment.CENTRE);// 设置居中
        cf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线

        int j = 0;// 行号标识
        width = colsNames.size();
        // 设置标题
        if (title != null && j == 0) {
            font.setBoldStyle(WritableFont.BOLD);
            Label header = new Label(0, j, title, cf);
            sheet.mergeCells(0, j, width - 1, j);// 合并单元格
            sheet.addCell(header);
            j++;
        }

        // 设置列名
        WritableFont colsFont = new WritableFont(WritableFont.ARIAL, 20);
        WritableCellFormat colscf = new WritableCellFormat(colsFont);
        colscf.setAlignment(Alignment.CENTRE);// 设置居中
        colscf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
        colscf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线
        for (int i = 0; i < colsNames.size(); i++) {
            // 设置指定内容的颜色 condition (e.g. key=value)
            for (String string : conditions) {
                int index = string.indexOf("=");
                int column = Integer.parseInt(string.substring(0, index));
                String value = string.substring(index + 1);
                if (column == i && colsNames.get(i).equals(value)) {
                    colsFont.setColour(Colour.RED);
                    break;
                }
            }

            // 合并指定值相同的列
            mergeColumns(sheet, j, colsNames, i, colmerge);

            Label cell = new Label(i, j, colsNames.get(i), colscf);
            sheet.addCell(cell);
            sheet.setColumnView(i, colsNames.get(i).getBytes().length);
        }
        j++;

        WritableFont bodyFont = new WritableFont(WritableFont.ARIAL);
        WritableCellFormat bodycf = new WritableCellFormat(bodyFont);
        bodycf.setAlignment(Alignment.CENTRE);// 设置居中
        bodycf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
        bodycf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线
        /*
         * for (List<String> cells : data) { for (int i = 0; i < cells.size();
         * i++) { // 设置指定内容的颜色 condition (e.g. key=value) for (String string :
         * conditions) { int index = string.indexOf("="); int column =
         * Integer.parseInt(string.substring(0, index)); String value =
         * string.substring(index + 1); if (column == i &&
         * cells.get(i).equals(value)) { bodyFont.setColour(Colour.RED); break;
         * } }
         *
         * // 合并指定值相同的列 mergeColumns(sheet, j, cells, i, colmerge);
         *
         * Label cell = new Label(i, j, cells.get(i), bodycf);
         * sheet.addCell(cell); }
         */
        // new WritableImage(x, y, width, height, imageData)
        List<List<String>> data = null;
        Label label = null;
        WritableImage image = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        // 插入数据
        for (Map.Entry<String, String> entry : goodImgs.entrySet()) {
            data = goodCodeSize.get(entry.getKey());
            sheet.mergeCells(0, j, 0, j + 15);
            label = new Label(0, j, entry.getKey(), bodycf);
            sheet.addCell(label);
            // sheet.mergeCells(3, j, 0, j + 10);
            // label = new Label(3, j, entry.getValue(), bodycf);
            try {
                byteArrayOutputStream = downloadFile(entry.getValue());
            } catch (IOException e) {
            }
            image = new WritableImage(3, j, 4, 10,
                    byteArrayOutputStream.toByteArray());
            sheet.addImage(image);
            // int col1, int row1, int col2, int row2

            for (int x = 0; x < data.size(); x++) {
                label = new Label(1, j + x, data.get(x).get(0), bodycf);
                sheet.addCell(label);
                label = new Label(2, j + x, data.get(x).get(1), bodycf);
                sheet.addCell(label);
            }
            j = j + 18;
        }

        // 设置底部
        if (foot != null) {
            Label footer = new Label(0, j, foot, cf);
            sheet.mergeCells(0, j, width - 1, j);// 合并单元格
            sheet.addCell(footer);
        }
    }

    /*****************************************
     * private method
     ********************************************************/
    /*
     * merge cells which contains same values in column by given column number
     *
     * @param sheet
     *
     * @param j row number
     *
     * @param cells cells of row
     *
     * @param i column number
     *
     * @param colsmerge merged column
     *
     * @throws WriteException
     *
     * @throws RowsExceededException
     */
    private static void mergeColumns(WritableSheet sheet, int j,
            List<String> cells, int i, String colsmerge)
            throws WriteException, RowsExceededException {

        if (colsmerge == null)
            return;

        String[] cols = colsmerge.split(",");
        for (String col : cols) {
            if (col != null && Integer.parseInt(col) == i) {
                int mergeColl = Integer.parseInt(col);
                String preCellValue = sheet.getCell(mergeColl, j - 1)
                        .getContents();
                if (cells.get(i).equals(preCellValue)) {
                    sheet.mergeCells(mergeColl, j - 1, mergeColl, j);
                    continue;
                }
            }
        }
    }

    /**
     * 下载图片
     *
     * @param fileUrl
     * @return
     * @throws IOException
     */
    public static ByteArrayOutputStream downloadFile(String fileUrl)
            throws IOException {

        URL url = new URL(fileUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        uc.setDoInput(true);// 设置是否要从 URL 连接读取数据,默认为true
        uc.connect();
        InputStream iputstream = uc.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = iputstream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        iputstream.close();
        return outSteam;
    }

    /**
     * @param title
     * @param parameters
     * @param list
     * @param request
     * @param response
     * @Title:export
     * @Description TODO(只有一级表头的表格导出到excel表格).
     * @date 2016年7月21日上午9:06:14
     * @author lzqiangPC
     */
    public static void export(String title, String[][] parameters,
            List<Map<String, Object>> list, HttpServletRequest request,
            HttpServletResponse response) {
        export(title, parameters, null, null, list, request, response);
    }

    /**
     * 根据单元格列表生成xls
     *
     * @param filePath
     * @param sheetName
     * @param labels
     */
    public static File export(String filePath, String sheetName,
            List<Label> labels) {
        WritableWorkbook workbook = null;
        File file = new File(filePath);
        try {
            workbook = jxl.Workbook.createWorkbook(file);
            WritableSheet sheet1 = workbook.createSheet(sheetName, 0);
            for (Label label : labels) {
                sheet1.addCell(label);
            }
            workbook.write();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException | WriteException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * @param title
     * @param parameters
     * @param list
     * @param request
     * @param response
     *            导出只有一级表头 合并 行列
     */
    public static void exportMergeRowColumn(String title, String[][] parameters,
            List<Map<String, Object>> list, HttpServletRequest request,
            HttpServletResponse response) {
        exportMegerRowColumn(title, parameters, null, null, list, request,
                response);
    }

    /**
     * @param title
     * @param parameters
     * @param twoTab
     * @param threeTab
     * @param list
     * @param request
     * @param response
     * @Title:export
     * @Description TODO(有三级表头的表格导出到excel表格中).
     * @date 2016年7月21日上午9:08:48
     * @author lzqiangPC
     */
    public static void export(String title, String[][] parameters,
            String[][] twoTab, String[][] threeTab,
            List<Map<String, Object>> list, HttpServletRequest request,
            HttpServletResponse response) {
        String downloadFilename;// 下载后的文件名称
        String filename = "";
        OutputStream out = null;
        if (list == null) {
            list = new ArrayList<Map<String, Object>>();
        }
        int count = (int) Math.ceil((double) list.size() / MAX_ROWS);// 产生excel的个数
        if (count > 1) {
            downloadFilename = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")
                    + ".zip";
        } else {
            downloadFilename = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")
                    + ".xls";
        }
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition",
                "attachment;filename=\"" + downloadFilename + "\"");

        if (count > 1) {// 进行切分导出
            File[] files = new File[count];
            try {
                String filePath = ResourceUtil.getUploadRootPath(request)
                        + "ExcelFolderTemp";
                File dirPath = new File(filePath);
                if (!dirPath.exists()) {
                    dirPath.mkdirs();
                }

                for (int i = 0; i < count; i++) {
                    filename = filePath + "/excel" + i + ".xls";
                    files[i] = new File(filename);
                    out = new FileOutputStream(new File(filename));
                    if (i == (count - 1)) {
                        baseexport(title, parameters, twoTab, threeTab,
                                list.subList(i * MAX_ROWS, list.size()), out);
                    } else {
                        baseexport(title, parameters, twoTab, threeTab,
                                list.subList(i * MAX_ROWS, (i + 1) * MAX_ROWS),
                                out);
                    }
                    out.close();
                }
                zipDownLoad(files, response);
                FileUtil.deleteDirs(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                baseexport(title, parameters, twoTab, threeTab, list,
                        response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param title
     * @param parameters
     * @param twoTab
     * @param threeTab
     * @param list
     * @param request
     * @param response
     *            合并行 列
     */
    public static void exportMegerRowColumn(String title, String[][] parameters,
            String[][] twoTab, String[][] threeTab,
            List<Map<String, Object>> list, HttpServletRequest request,
            HttpServletResponse response) {
        String downloadFilename;// 下载后的文件名称
        String filename = "";
        OutputStream out = null;
        if (list == null) {
            list = new ArrayList<Map<String, Object>>();
        }
        int count = (int) Math.ceil((double) list.size() / MAX_ROWS);// 产生excel的个数
        if (count > 1) {
            downloadFilename = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")
                    + ".zip";
        } else {
            downloadFilename = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")
                    + ".xls";
        }
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition",
                "attachment;filename=\"" + downloadFilename + "\"");

        if (count > 1) {// 进行切分导出
            File[] files = new File[count];
            try {
                String filePath = ResourceUtil.getUploadRootPath(request)
                        + "ExcelFolderTemp";
                File dirPath = new File(filePath);
                if (!dirPath.exists()) {
                    dirPath.mkdirs();
                }

                for (int i = 0; i < count; i++) {
                    filename = filePath + "/excel" + i + ".xls";
                    files[i] = new File(filename);
                    out = new FileOutputStream(new File(filename));
                    if (i == (count - 1)) {
                        baseexportMegerRowColumn(title, parameters, twoTab,
                                threeTab,
                                list.subList(i * MAX_ROWS, list.size()), out);
                    } else {
                        baseexportMegerRowColumn(title, parameters, twoTab,
                                threeTab,
                                list.subList(i * MAX_ROWS, (i + 1) * MAX_ROWS),
                                out);
                    }
                    out.close();
                }
                zipDownLoad(files, response);
                FileUtil.deleteDirs(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                baseexportMegerRowColumn(title, parameters, twoTab, threeTab,
                        list, response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param title
     *            表格标题(可为空)
     * @param parameters
     *            需要导出的字段及其文本含意
     * @param list
     *            数据集合
     * @param out
     *            文件输出流
     * @return
     * @throws Exception
     *             异常
     * @Title:导出Excel.
     * @date: 2015-7-10 下午12:43:35
     * @author: luyao
     */
    public static void baseexport(String title, String[][] parameters,
            String[][] oneTab, String[][] twoTab,
            List<Map<String, Object>> list, OutputStream out) throws Exception {
        // 工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 工作表
        HSSFSheet sheet = wb.createSheet("Sheet1");

        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 生成标题样式
        HSSFCellStyle style1 = wb.createCellStyle();
        // 设置这些样式
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFillForegroundColor(HSSFColor.WHITE.index);
        // 生成一个字体
        HSSFFont font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 12);
        font1.setBold(true);
        // 把字体应用到当前的样式
        style1.setFont(font1);

        // 生成数据样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        //// 居中
        style2.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        style2.setVerticalAlignment(
                org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
        ;// 垂直居中
        style2.setFillForegroundColor(HSSFColor.WHITE.index);

        // 生成另一个字体
        HSSFFont font2 = wb.createFont();
        font2.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 设置表格标题行 表头
        int index = 0;
        if (title != null && !title.equals("")) {
            // 合并单元格
            CellRangeAddress cra = new CellRangeAddress(0, 0, 0,
                    parameters.length - 1);
            // 在sheet里增加合并单元格
            sheet.addMergedRegion(cra);
            Row row1 = sheet.createRow(0);
            Cell cell1 = row1.createCell(0);
            cell1.setCellStyle(style1);
            cell1.setCellValue(title);
            index++;
        }

        HSSFRow row3 = sheet.createRow(index);
        if (null != twoTab) {
            index++;
        }
        HSSFRow row2 = sheet.createRow(index);
        if (null != oneTab) {
            index++;// 没有二级表头
        }
        HSSFRow row = sheet.createRow(index);

        // 产生表格列标行
        for (int i = 0; i < parameters.length; i++) {
            int twoSpanIndex = isHaveField(parameters[i][0], twoTab);
            int colspanIndex = isHaveField(parameters[i][0], oneTab);
            if (twoSpanIndex >= 0) {
                insertMoreTab(twoTab, sheet, style1, row3, i, twoSpanIndex);// 插入三级表头
            }
            if (colspanIndex >= 0) {
                insertMoreTab(oneTab, sheet, style1, row2, i, colspanIndex);// 插入二级表头
            }
            insertOnTab(parameters, style1, row, i);// 插入一集表头
        }

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(++index);
            Map<String, Object> map = list.get(i);
            for (int j = 0; j < parameters.length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellStyle(style2);
                if (map.containsKey(parameters[j][0])) {
                    if (map.get(parameters[j][0]) != null) {
                        cell.setCellValue(map.get(parameters[j][0]).toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }
        }

        wb.write(out);
        wb.close();
    }

    /**
     * @param title
     *            表格标题(可为空)
     * @param parameters
     *            需要导出的字段及其文本含意
     * @param list
     *            数据集合
     * @param out
     *            文件输出流
     * @return
     * @throws Exception
     *             异常
     * @Title:导出Excel.
     * @date: 2015-7-10 下午12:43:35
     * @author: luyao
     */
    public static void baseexportRow(String title, String[][] parameters,
            String[][] oneTab, String[][] twoTab,
            List<Map<String, Object>> list, OutputStream out) throws Exception {
        // 工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 工作表
        HSSFSheet sheet = wb.createSheet("Sheet1");

        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 生成标题样式
        HSSFCellStyle style1 = wb.createCellStyle();
        // 设置这些样式
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFillForegroundColor(HSSFColor.WHITE.index);
        // 生成一个字体
        HSSFFont font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 12);
        font1.setBold(true);
        // 把字体应用到当前的样式
        style1.setFont(font1);

        // 生成数据样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(
                org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
        style2.setFillForegroundColor(HSSFColor.WHITE.index);

        // 生成另一个字体
        HSSFFont font2 = wb.createFont();
        font2.setBold(true);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 设置表格标题行 表头
        int index = 0;
        if (title != null && !title.equals("")) {
            // 合并单元格
            CellRangeAddress cra = new CellRangeAddress(0, 0, 0,
                    parameters.length - 1);
            // 在sheet里增加合并单元格
            sheet.addMergedRegion(cra);
            Row row1 = sheet.createRow(0);
            Cell cell1 = row1.createCell(0, parameters.length - 1);
            cell1.setCellStyle(style1);
            cell1.setCellValue(title);
            index++;
        }

        HSSFRow row3 = sheet.createRow(index);
        if (null != twoTab) {
            index++;
        }
        HSSFRow row2 = sheet.createRow(index);
        if (null != oneTab) {
            index++;// 没有二级表头
        }
        HSSFRow row = sheet.createRow(index);

        // 产生表格列标行
        for (int i = 0; i < parameters.length; i++) {
            int twoSpanIndex = isHaveField(parameters[i][0], twoTab);
            int colspanIndex = isHaveField(parameters[i][0], oneTab);
            if (twoSpanIndex >= 0) {
                insertMoreTab(twoTab, sheet, style1, row3, i, twoSpanIndex);// 插入三级表头
            }
            if (colspanIndex >= 0) {
                insertMoreTab(oneTab, sheet, style1, row2, i, colspanIndex);// 插入二级表头
            }
            insertOnTab(parameters, style1, row, i);// 插入一集表头
        }

        // 行合并
        Map<String, RowMerge> mapRow = new HashMap<String, RowMerge>();
        RowMerge merge = null;
        // 列合并
        Map<String, ColumnMerge> columnMergeMap = new HashMap<String, ColumnMerge>();
        ColumnMerge columnMerge = null;

        // 表头合并列
        for (int i = 0; i < parameters.length; i++) {
            // 列合并

            if (columnMergeMap.containsKey(parameters[i][1] + index)) {
                columnMerge = columnMergeMap.get(parameters[i][1] + index);
                columnMerge.calculate(i);
            } else {
                columnMerge = new ColumnMerge(i, index, parameters[i][1]);
                columnMergeMap.put(parameters[i][1] + index, columnMerge);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(++index);
            Map<String, Object> map = list.get(i);
            for (int j = 0; j < parameters.length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellStyle(style2);
                if (map.containsKey(parameters[j][0])) {
                    if (map.get(parameters[j][0]) != null) {
                        cell.setCellValue(map.get(parameters[j][0]).toString());
                    } else {
                        cell.setCellValue("");
                    }
                    // 行合并
                    if (mapRow.containsKey(
                            map.get(parameters[j][0]).toString() + j)) {
                        merge = mapRow
                                .get(map.get(parameters[j][0]).toString() + j);
                        merge.calculate(index);
                    } else {
                        merge = new RowMerge(index, j,
                                map.get(parameters[j][0]).toString());
                        mapRow.put(map.get(parameters[j][0]).toString() + j,
                                merge);
                    }

                    // 列合并

                    if (columnMergeMap.containsKey(
                            map.get(parameters[j][0]).toString() + index)) {
                        columnMerge = columnMergeMap.get(
                                map.get(parameters[j][0]).toString() + index);
                        columnMerge.calculate(j);
                    } else {
                        columnMerge = new ColumnMerge(j, index,
                                map.get(parameters[j][0]).toString());
                        columnMergeMap.put(
                                map.get(parameters[j][0]).toString() + index,
                                columnMerge);
                    }
                }
            }
        }

        // 行合并
        if (!mapRow.isEmpty()) {
            for (Map.Entry<String, RowMerge> entry : mapRow.entrySet()) {
                merge = entry.getValue();
                if (merge.isMerge()) {
                    sheet.addMergedRegion(new CellRangeAddress(
                            merge.getStartRowIndex(), merge.getLastRowIndex(),
                            merge.getColumnIndex(), merge.getColumnIndex()));
                }
            }
        }

        // 列合并
        if (!columnMergeMap.isEmpty()) {
            for (Map.Entry<String, ColumnMerge> entry : columnMergeMap
                    .entrySet()) {
                columnMerge = entry.getValue();
                if (columnMerge.isMerge()) {
                    sheet.addMergedRegion(
                            new CellRangeAddress(columnMerge.getRowIndex(),
                                    columnMerge.getRowIndex(),
                                    columnMerge.getStartColumnIndex(),
                                    columnMerge.getLastColumnIndex()));
                }
            }
        }

        wb.write(out);
        wb.close();
    }

    /**
     * @param title
     * @param parameters
     * @param oneTab
     * @param twoTab
     * @param list
     * @param out
     * @throws Exception
     *             通过列名取值 合并 行列
     */
    public static void baseexportMegerRowColumn(String title,
            String[][] parameters, String[][] oneTab, String[][] twoTab,
            List<Map<String, Object>> list, OutputStream out) throws Exception {
        // 工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 工作表
        HSSFSheet sheet = wb.createSheet("Sheet1");

        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 生成标题样式
        HSSFCellStyle style1 = wb.createCellStyle();
        // 设置这些样式
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFillForegroundColor(HSSFColor.WHITE.index);
        // 生成一个字体
        HSSFFont font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 12);
        font1.setBold(true);
        // 把字体应用到当前的样式
        style1.setFont(font1);

        // 生成数据样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(
                org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
        style2.setFillForegroundColor(HSSFColor.WHITE.index);

        // 生成另一个字体
        HSSFFont font2 = wb.createFont();
        font2.setBold(true);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 设置表格标题行 表头
        int index = 0;
        if (title != null && !title.equals("")) {
            // 合并单元格
            CellRangeAddress cra = new CellRangeAddress(0, 0, 0,
                    parameters.length - 1);
            // 在sheet里增加合并单元格
            sheet.addMergedRegion(cra);
            Row row1 = sheet.createRow(0);
            Cell cell1 = row1.createCell(0, parameters.length - 1);
            cell1.setCellStyle(style1);
            cell1.setCellValue(title);
            index++;
        }

        HSSFRow row3 = sheet.createRow(index);
        if (null != twoTab) {
            index++;
        }
        HSSFRow row2 = sheet.createRow(index);
        if (null != oneTab) {
            index++;// 没有二级表头
        }
        HSSFRow row = sheet.createRow(index);

        // 产生表格列标行
        for (int i = 0; i < parameters.length; i++) {
            int twoSpanIndex = isHaveField(parameters[i][0], twoTab);
            int colspanIndex = isHaveField(parameters[i][0], oneTab);
            if (twoSpanIndex >= 0) {
                insertMoreTab(twoTab, sheet, style1, row3, i, twoSpanIndex);// 插入三级表头
            }
            if (colspanIndex >= 0) {
                insertMoreTab(oneTab, sheet, style1, row2, i, colspanIndex);// 插入二级表头
            }
            insertOnTab(parameters, style1, row, i);// 插入一集表头
        }

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(++index);
            Map<String, Object> map = list.get(i);
            for (int j = 0; j < parameters.length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellStyle(style2);
                if (map.containsKey(parameters[j][0])) {
                    if (map.get(parameters[j][0]) != null) {
                        cell.setCellValue(map.get(parameters[j][0]).toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }
        }

        wb.write(out);
        wb.close();
    }

    /**
     * @param files
     * @param out
     * @throws Exception
     * @Title:zipDownLoad
     * @Description TODO(zip打包下载).
     * @date 2015年8月21日
     * @author lzqiangPC
     */
    public static void zipDownLoad(File[] files, HttpServletResponse response)
            throws Exception {
        OutputStream out = response.getOutputStream();
        ZipOutputStream zos = new ZipOutputStream(out);
        try {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                zos.putNextEntry(new ZipEntry(f.getName()));
                FileInputStream fis = new FileInputStream(f);
                byte[] buffer = new byte[1024];
                int r = 0;
                while ((r = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, r);
                }
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            LOG.error(e);
        } finally {
            zos.flush();
            zos.close();
            out.close();
        }
    }

    /**
     * 校验excel并将结果封装到bean中放入到list中 并校验数据合法性
     *
     * @param list
     *            从excel表格中读取的list
     * @param clazz
     *            保存的实体类
     * @param controllerId
     *            所在的控制器类的id 使用注解 默认值时类的首字母小写
     * @param validationstr
     *            校验用的json字符串
     * @return Object 如果object为String类型则是返回的错误信息，如果为list则是返回的 Bean的list集合
     * @throws Exception
     */
    public static Object validation(List<Map<String, Object>> list,
            Class<?> clazz, String controllerId, String validationstr)
            throws Exception {
        List<Object> listbean = new ArrayList<Object>();
        Map<String, Object> params = null;
        ValidationBean[] validation;
        String type = String.class.getName();// 字段类型 默认String类型
        try {// 解析json字符串
            validation = JSONUtil.json2obj(validationstr,
                    ValidationBean[].class);
        } catch (Exception e) {
//            LOG.info("校验json解析错误,查看json是否正确");
            throw new RuntimeException("校验json解析错误,查看json是否正确");
        }
        for (int i = 0; i < list.size(); i++) {
            params = new HashMap<String, Object>();
            for (ValidationBean obj : validation) {
                if (obj.fieldname == null) {
                    throw new RuntimeException("校验json错误,fieldname字段不可为空");
                }
                Object fieldvalue = list.get(i).get(obj.fieldname);

                if (obj.type != null) {
                    type = obj.type;
                }
                // fieldvalue.getClass().getName()获取该字段值的真实类型 不判断String类型
                if (fieldvalue != null && !"".equals(fieldvalue.toString())
                        && !type.equals(String.class.getName())
                        && !fieldvalue.getClass().getName().toLowerCase()
                                .equals(type.toLowerCase())) {
                    return fieldvalue + "格式错误";
                }
                // 如果remote！=null 是充远程中获取值的，这里肯定是null
                if (obj.notempty && obj.remote == null) {// 类型正确,判断是否可以为空
                    // 不可为空
                    if (fieldvalue == null
                            || StringUtil.isBlank(fieldvalue.toString())) {
                        return obj.message;
                    }
                }
                // 类型正确,判断长度是否正确,时间类型不校验 没写长度则不验证
                if (obj.length != null && fieldvalue != null
                        && !(fieldvalue instanceof Date)
                        && !(fieldvalue instanceof Number)
                        && (fieldvalue.toString().length() < obj.length.min
                                || fieldvalue.toString()
                                        .length() > obj.length.max)) {
                    return obj.message;
                }
                if (obj.remote != null) {
                    Map<String, String> invokeMap = invokeMethod(
                            obj.remote.method, obj.remote.paramnames,
                            list.get(i), controllerId);
                    if (invokeMap != null
                            && invokeMap.get(EXCEL_ERRORMSG) != null) {// 有错误信息
                        return invokeMap.get(EXCEL_ERRORMSG);
                    } else if (invokeMap != null
                            && invokeMap.get(ID_VALUE) != null) {// 有要存入数据库中的返回值
                        fieldvalue = invokeMap.get(ID_VALUE);
                        if (obj.notempty
                                && (null == fieldvalue || fieldvalue == "")) {
                            return obj.message;
                        }
                    }
                }
                // 将字段和值添加到params中，然后进行封装到Bean中
                params.put(obj.fieldname, fieldvalue);
            }
            Object clazzs = clazz.newInstance();
            BeanUtils.populate(clazzs, params);
            listbean.add(clazzs);
        }
        return listbean;
    }

    /**
     * 利用反射执行方法
     *
     * @param methods
     * @param params
     * @param map
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, String> invokeMethod(String methods,
            String params, Map<String, Object> map, String controllerId) {
        if (!StringUtil.isBlank(methods) && !StringUtil.isBlank(params)) {
            String[] paramArray = params.split(",");
            Class[] clazz = new Class[paramArray.length];
            Object[] obj = new Object[paramArray.length];
            for (int i = 0; i < paramArray.length; i++) {
                clazz[i] = String.class;
                obj[i] = map.get(paramArray[i]);
            }
            try {
                WebApplicationContext wac = ContextLoader
                        .getCurrentWebApplicationContext();
                Object controller = wac.getBean(controllerId);
                Method method = controller.getClass().getMethod(methods, clazz);
                return (Map<String, String>) method.invoke(controller, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param path
     *            文件全路径
     * @param items
     *            导出的字段，对应表格字段
     * @param clazz
     *            实体类
     * @return List<Object>
     * @throws Exception
     *             异常
     * @Title:读取excel文件
     * @date: 2015-7-8 下午3:01:35
     * @author: luyao
     */
    public static List<Object> read(String path, String[] items, Class<?> clazz)
            throws Exception {
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = null;
        // Sheet1
        HSSFSheet hssfSheet = hssfWorkbook.getSheet("Sheet1");
        // 循环行Row
        for (int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow != null) {
                map = new Hashtable<String, Object>();
                for (int i = 0; i < items.length; i++) {
                    if (hssfRow.getCell(i) != null
                            && hssfRow.getCell(i).toString() != "") {
                        // map.put(items[i], hssfRow.getCell(i)
                        // .getStringCellValue().toString());
                        // map.put(items[i], getValue(hssfRow.getCell(i)));
                        map.put(items[i], superOrSubScript2003(
                                hssfRow.getCell(i), hssfWorkbook));
                    }
                    // map.put(items[i], getValue(hssfRow.getCell(i)));
                }
                Object object = BeanUtil.toObject(map, clazz);
                list.add(object);
            }
        }
        hssfWorkbook.close();
        return list;
    }

    /**
     * @param files
     * @param out
     * @throws Exception
     * @Title:superOrSubScript2003
     * @Description TODO(处理上标 、 下标).
     * @date 2016年6月22日
     * @author luyao
     */
    public static String superOrSubScript2003(Cell cell, Workbook book) {
        HSSFWorkbook workbook = null;
        HSSFFont font = null;
        HSSFRichTextString rts = null;
        HSSFCellStyle style = null;
        int fromIndex = 0;
        int toIndex = 0;
        String value = "";
        // 处理上下标
        workbook = (HSSFWorkbook) book;
        // 判断当前单元格的内容是否为数字类型，如果是转换成字符串型
        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            cell.setCellValue((cell.getNumericCellValue() + "").substring(0,
                    (cell.getNumericCellValue() + "").indexOf(".")));
        }
        // 获取单元格中的数据
        rts = (HSSFRichTextString) cell.getRichStringCellValue();
        // 获取每个单元格数据的style属性
        style = (HSSFCellStyle) cell.getCellStyle();
        font = style.getFont(workbook);
        if (rts.numFormattingRuns() > 0) {
            for (int k = 0; k < rts.numFormattingRuns(); k++) {
                toIndex = rts.getIndexOfFormattingRun(k);
                String temp = rts.toString().substring(fromIndex, toIndex);

                // 判断上标
                if (font.getTypeOffset() == HSSFFont.SS_SUPER) {
                    temp = "<sup>" + temp + "</sup>";
                    // System.out.println("\t______________发现上标");
                }
                // 判断下标
                if (font.getTypeOffset() == HSSFFont.SS_SUB) {
                    temp = "<sub>" + temp + "</sub>";
                    // System.out.println("\t______________发现下标");
                }
                value += temp;
                if (!value.equals("")) {
                    font = workbook.getFontAt(rts.getFontOfFormattingRun(k));
                }
                fromIndex = toIndex;
            }
            toIndex = rts.length();
            String temp1 = rts.toString().substring(fromIndex, toIndex);
            // System.out.println("\tSubstring [" + temp1 + "]");
            if (font.getTypeOffset() == HSSFFont.SS_SUPER) {
                temp1 = "<sup>" + temp1 + "</sup>";
                // System.out.println("\t______________发现上标");
            }
            if (font.getTypeOffset() == HSSFFont.SS_SUB) {
                // System.out.println("\t______________发现下标");
            }
            value += temp1;
            return value;
        }
        return cell.toString();
    }

    /*********************
     * excel表格读取和校验开始
     ************************************************************************/
    /**
     * 读取excel表格内容,将内容放入list中
     *
     * @param path
     *            excel上传的服务器后的路径
     * @param validationstr
     *            验证规则 字段顺序要和excel表格中的字段顺序一致
     * @return 读取的excel表格的数据放入到list中
     * @throws Exception
     * @date 2016/10/18
     * @author lzqiangPC
     */
    public static List<Map<String, Object>> read(String path,
            String validationstr) throws Exception {
        ValidationBean[] validation;
        try {// 解析json字符串
            validation = JSONUtil.json2obj(validationstr,
                    ValidationBean[].class);
        } catch (Exception e) {
            throw new RuntimeException("校验json解析错误,查看json是否正确");
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        InputStream is = null;
        Workbook workbook = null;
        try {
            is = new FileInputStream(path);
            workbook = new HSSFWorkbook(is);
            Map<String, Object> map = null;
            Sheet sheet = workbook.getSheetAt(0);// 第一个工作区域 Sheet1
            // 循环行Row
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    map = new HashMap<String, Object>();
                    for (int i = 0; i < validation.length; i++) {
                        if (ExcelUtil.getValue(row.getCell(i)) != null
                                && ExcelUtil.getValue(row.getCell(i)) != "")
                            map.put(validation[i].fieldname,
                                    ExcelUtil.getValue(row.getCell(i)));
                    }
                    list.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != workbook) {
                workbook.close();
            }
            if (null != is) {
                is.close();
            }
        }
        return list;
    }

    /**
     * @param cell
     *            HSSFCell
     * @return Object
     * @Title:getValue(转换数据类型).
     * @date: 2015-7-2 上午11:45:16
     * @author: luyao
     */
    public static Object getValue(Cell cell) {
        String returnValue = "";
        if (null != cell) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                            .getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    return date;
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil
                            .getJavaDate(value);
                    return date;
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#");
                    }
                    returnValue = format.format(value);
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                returnValue = cell.getStringCellValue();
                if (returnValue.equals("男")) {
                    returnValue = "0";
                } else if (returnValue.equals("女")) {
                    returnValue = "1";
                }
                return returnValue;
            case Cell.CELL_TYPE_BOOLEAN: // 布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                returnValue = null;
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                returnValue = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                returnValue = "";
                break;
            default:
//                LOG.info("未知类型!");
                break;
            }
        }
        return returnValue;
    }

    /**
     * @param filePath
     * @return
     * @Title:isExcel2003
     * @Description TODO(是否是2003的excel ， 返回true是2003). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2017年7月14日
     * @author Administrator
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * @param filePath
     * @return
     * @Title:isExcel2007
     * @Description TODO(是否是2007的excel ， 返回true是2007). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2017年7月14日
     * @author Administrator
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * @param value
     * @param colspan
     * @return
     * @Title:isHaveField
     * @Description TODO(判断数值在不在二维数组colspan的第一个位置).
     * @date 2016年7月20日下午2:48:33
     * @author lzqiangPC
     */
    private static int isHaveField(String value, String[][] colspan) {
        if (null != colspan) {
            for (int i = 0; i < colspan.length; i++) {
                if (colspan[i][0].equals(value)) {
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }

    /**
     * @param oneTab
     * @param sheet
     * @param style1
     * @param row2
     * @param i
     * @param colspanIndex
     * @Title:insertMoreTab
     * @Description TODO(填充二级或三级表头).
     * @date 2016年7月20日下午4:01:47
     * @author lzqiangPC
     */
    private static void insertMoreTab(String[][] oneTab, HSSFSheet sheet,
            HSSFCellStyle style1, HSSFRow row2, int i, int colspanIndex) {
        HSSFCell cell = row2.createCell(i);
        CellRangeAddress cra = new CellRangeAddress(row2.getRowNum(),
                row2.getRowNum(), i,
                i + Integer.parseInt(oneTab[colspanIndex][2]) - 1);
        sheet.addMergedRegion(cra);
        for (int j = i; j < (i
                + Integer.parseInt(oneTab[colspanIndex][2])); j++) {
            HSSFCell cel2 = row2.createCell(j);
            cel2.setCellStyle(style1);// 合并单元格后边框线会没有 这样可以解决该问题
        }
        cell.setCellStyle(style1);
        cell.setCellValue(oneTab[colspanIndex][1]);
    }

    /**
     * @param parameters
     * @param style1
     * @param row
     * @param i
     * @Title:insertOnTab
     * @Description TODO(填充一级表头).
     * @date 2016年7月20日下午4:02:07
     * @author lzqiangPC
     */
    private static void insertOnTab(String[][] parameters, HSSFCellStyle style1,
            HSSFRow row, int i) {
        HSSFCell cell = row.createCell(i);
        cell.setCellStyle(style1);
        HSSFRichTextString text = new HSSFRichTextString(parameters[i][1]);
        cell.setCellValue(text);
    }

    /**
     * @param file
     *            文件
     * @param headIndex
     *            标题下标
     * @param contentIndex
     *            内容开始下标
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static List<Map<String, Object>> readExcel(File file, int headIndex,
            int contentIndex) throws IOException {
        List<Map<String, Object>> listMapContent = null;
        if (isExcel2007(file.getName())) {
            listMapContent = createExcelListMapContent2007(file, headIndex,
                    contentIndex);
        }
        if (isExcel2003(file.getName())) {
            listMapContent = createExcelListMapContent2003(file, headIndex,
                    contentIndex);
        }
        return listMapContent;
    }

    /**
     * 2003 Excel读取
     *
     * @param file
     *            文件
     * @param headIndex
     * @param contentIndex
     * @return
     * @throws Exception
     * @throws IOException
     */
    private static List<Map<String, Object>> createExcelListMapContent2003(
            File file, int headIndex, int contentIndex) throws IOException {

        List<Map<String, Object>> listMapContent = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            HSSFWorkbook hSSFWorkbook = new HSSFWorkbook(is);
            listMapContent = new LinkedList<>();
            // 标题
            Map<Integer, String> headTitleMap;
            // 内容
            Map<String, Object> contentMap;

            for (int numSheet = 0; numSheet < hSSFWorkbook
                    .getNumberOfSheets(); numSheet++) {
                // 读取页
                HSSFSheet hssfSheet = hSSFWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                headTitleMap = new LinkedHashMap<Integer, String>();
                // 读取标题
                HSSFRow hssfRow = hssfSheet.getRow(headIndex);
                for (int head = 0; head < hssfRow
                        .getPhysicalNumberOfCells(); head++) {
                    headTitleMap.put(head,
                            getStringValue2003(hssfRow.getCell(head)));
                }
                System.out.println(JSONObject.toJSONString(headTitleMap));
                // Read the Row
                for (int rowNum = contentIndex; rowNum <= hssfSheet
                        .getLastRowNum(); rowNum++) {
                    hssfRow = hssfSheet.getRow(rowNum);
                    contentMap = new LinkedHashMap<>();
                    for (int content = 0; content < headTitleMap
                            .size(); content++) {
                        contentMap.put(headTitleMap.get(content),
                                getStringValue2003(hssfRow.getCell(content)));
                    }
                    listMapContent.add(contentMap);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
        }

        return listMapContent;
    }

    /**
     * 2007 Excel读取
     *
     * @param fis
     *            文件
     * @param headIndex
     * @param contentIndex
     * @return
     * @throws IOException
     */
    private static List<Map<String, Object>> createExcelListMapContent2007(
            File file, int headIndex, int contentIndex) throws IOException {
        InputStream is = null;
        List<Map<String, Object>> listMapContent = null;
        try {
            listMapContent = new LinkedList<>();
            is = new FileInputStream(file);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            // 标题
            Map<Integer, String> headTitleMap;
            // 内容
            Map<String, Object> contentMap;
            for (int numSheet = 0; numSheet < xssfWorkbook
                    .getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                headTitleMap = new LinkedHashMap<Integer, String>();
                // 读取标题
                XSSFRow xssfRow = xssfSheet.getRow(headIndex);
                for (int head = 0; head < xssfRow
                        .getPhysicalNumberOfCells(); head++) {
                    headTitleMap.put(head,
                            getStringValue2007(xssfRow.getCell(head)));
                }
                System.out.println(JSONObject.toJSONString(headTitleMap));
                for (int rowNum = contentIndex; rowNum <= xssfSheet
                        .getLastRowNum(); rowNum++) {
                    xssfRow = xssfSheet.getRow(rowNum);
                    contentMap = new LinkedHashMap<>();
                    for (int content = 0; content < headTitleMap
                            .size(); content++) {
                        contentMap.put(headTitleMap.get(content),
                                getStringValue2007(xssfRow.getCell(content)));
                    }
                    listMapContent.add(contentMap);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return listMapContent;
    }

    /**
     * @param row
     * @return 判断行是否为空
     */
    public boolean isRowEmpty2003(HSSFRow row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            HSSFCell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param row
     * @return 判断行是否为空
     */
    public boolean isXssfRowEmpty2007(XSSFRow row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            XSSFCell cell = row.getCell(i);
            if (cell != null && cell.getCellType() == cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param hssfCell
     * @return 判断值
     */
    private static String getStringValue2003(HSSFCell hssfCell) {
        if (hssfCell != null
                && hssfCell.getCellType() == hssfCell.CELL_TYPE_STRING) {
            return hssfCell.getStringCellValue().trim();
        } else if (hssfCell != null
                && hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date date = HSSFDateUtil
                        .getJavaDate(hssfCell.getNumericCellValue());
                String value = sdf.format(date);
                return value;
            }
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return null;
        }
    }

    /**
     * @param xssfCell
     * @return 判断值
     */
    private static String getStringValue2007(XSSFCell xssfCell) {
        if (xssfCell != null
                && xssfCell.getCellType() == xssfCell.CELL_TYPE_STRING) {
            return xssfCell.getStringCellValue().trim();
        } else if (xssfCell != null
                && xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date date = HSSFDateUtil
                        .getJavaDate(xssfCell.getNumericCellValue());
                String value = sdf.format(date);
                return value;
            }
            return String.valueOf(xssfCell.getNumericCellValue());
        } else {
            return null;
        }
    }

    /**
     * @param hssfCell
     * @return 判断数据值
     */
    private static double getDoubleValue2003(HSSFCell hssfCell) {
        double result = 0;
        if (hssfCell != null) {
            if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
                result = hssfCell.getNumericCellValue();
            } else if (hssfCell.getStringCellValue() != null
                    && !"".equals(hssfCell.getStringCellValue())) {
                result = Double.parseDouble(hssfCell.getStringCellValue());
            }
        }
        return result;
    }

    /**
     * @param xssfCell
     * @return 判断数据值
     */
    private static double getDoubleValue2007(XSSFCell xssfCell) {
        double result = 0;
        if (xssfCell != null) {
            if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
                result = xssfCell.getNumericCellValue();
            } else if (xssfCell.getStringCellValue() != null
                    && !"".equals(xssfCell.getStringCellValue())) {
                result = Double.parseDouble(xssfCell.getStringCellValue());
            }
        }
        return result;
    }

    /**
     * @param hssfCell
     * @return 判断数据值
     */
    private static BigDecimal getBigDecimalValue2003(HSSFCell hssfCell) {
        BigDecimal result = new BigDecimal(0);
        if (hssfCell != null) {
            if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
                result = new BigDecimal(hssfCell.getNumericCellValue());
            } else if (hssfCell.getStringCellValue() != null
                    && !"".equals(hssfCell.getStringCellValue())) {
                result = new BigDecimal(hssfCell.getStringCellValue());
            } else {
                result = new BigDecimal("0");
            }
        }
        return result;
    }

    /**
     * @param xssfCell
     * @return 判断数据值
     */
    private static BigDecimal getBigDecimalValue2007(XSSFCell xssfCell) {
        BigDecimal result = new BigDecimal(0);
        if (xssfCell != null) {
            if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
                result = new BigDecimal(xssfCell.getNumericCellValue());
            } /*
               * else if(xssfCell.getStringCellValue()!=null &&
               * !"".equals(xssfCell.getStringCellValue())){ result = new
               * BigDecimal(xssfCell.getStringCellValue()); }
               */ else {
                result = new BigDecimal("0");
            }
        }
        return result;
    }

    /**
     * @param hssfCell
     * @return 判断数据时期
     */
    private static Date getDateValue2003(HSSFCell hssfCell) {
        Date result = null;
        if (hssfCell != null) {
            if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
                result = hssfCell.getDateCellValue();
            } /*
               * else { result =
               * Double.parseDouble(hssfCell.getStringCellValue()); }
               */
        }
        return result;
    }

    /**
     * @param hssfCell
     * @return 判断数据时期
     */
    private static Date getDateValue2007(XSSFCell xssfCell) {
        Date result = null;
        if (xssfCell != null) {
            if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
                result = xssfCell.getDateCellValue();
            } /*
               * else { result =
               * Double.parseDouble(hssfCell.getStringCellValue()); }
               */
        }
        return result;
    }

    public static Label createErrorCell(int column, int row, String content,
            String errorMessage) throws WriteException {
        WritableFont colsFont = new WritableFont(WritableFont.ARIAL);
        WritableCellFeatures cellFeatures = new WritableCellFeatures();
        cellFeatures.setComment(errorMessage);// 增加批注
        WritableCellFormat colscf = new WritableCellFormat(colsFont);
        colscf.setAlignment(Alignment.CENTRE);// 设置居中
        colscf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
        colscf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线
        colsFont.setColour(Colour.RED);
        Label label = new Label(column, row, content, colscf);
        label.setCellFeatures(cellFeatures);
        return label;
    }

    public static Label createNormalCell(int column, int row, String obj)
            throws WriteException {
        WritableFont colsFont = new WritableFont(WritableFont.ARIAL);
        WritableCellFormat colscf = new WritableCellFormat(colsFont);
        colscf.setAlignment(Alignment.CENTRE);// 设置居中
        colscf.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置垂直居中
        colscf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框线
        colsFont.setColour(Colour.BLACK);
        return new Label(column, row, obj, colscf);
    }

    /**
     * jxl
     *
     * @param errorCells
     * @param row
     * @param objs
     * @param errorMessage
     * @param column
     * @throws WriteException
     */
    public static void buildErrorCell(List<Label> errorCells, int row,
            Object[] objs, String errorMessage, int column)
            throws WriteException {

        for (int i = 0; i < objs.length; i++) {
            Label cell;
            if (i == column) {
                cell = createErrorCell(i, row, objs[i].toString(),
                        errorMessage);
            } else {
                cell = createNormalCell(i, row, objs[i].toString());
            }
            errorCells.add(cell);
        }
    }

    /**
     * 错误行 生成
     *
     * @param errorCells
     * @param row
     * @param objs
     * @throws WriteException
     */
    public static void buildErrorCell(List<Label> errorCells, int row,
            Object[] objs) throws WriteException {
        for (int i = 0; i < objs.length; i++) {
            Label cell = createNormalCell(i, row, (String) objs[i]);
            errorCells.add(cell);
        }
    }

}
