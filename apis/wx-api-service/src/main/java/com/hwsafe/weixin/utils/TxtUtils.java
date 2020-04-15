package com.hwsafe.weixin.utils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hwsafe.weixin.utils.Constants.Letters;

/**
 * 
 * @Description:文本文件读写
 * @ClassName TxtUtils
 * @author: xueshuailing
 * @Created 2016 2016年5月13日 下午2:19:46
 */
public class TxtUtils {

    /**
     * @Description:
     * @param inputStream
     *            读取文件
     * @throws Exception
     * @ReturnType void
     * @author: shosho
     * @Created 2016 2016年5月31日 上午9:37:57
     */
    public static List<String[]> readTxt(InputStream inputStream)
            throws Exception {
        return readTxt(inputStream, Letters.SPILT, Constants.Charset.UTF8);
    }

    /**
     * @Description: 服务文件
     * @param inputStream
     *            文件流
     * @param delimiter
     *            分隔符
     * @throws Exception
     * @ReturnType void
     * @author: shosho
     * @Created 2016 2016年5月31日 上午9:37:04
     */
    public static List<String[]> readTxt(InputStream inputStream,
            char delimiter) throws Exception {
        return readTxt(inputStream, delimiter, Constants.Charset.UTF8);
    }

    /**
     * @Description:
     * @param inputStream
     *            文件流
     * @param split
     *            分隔符
     * @param charset
     *            支付编码
     * @throws Exception
     * @ReturnType List<String[]> 包含头的
     * @author: shosho
     * @Created 2016 2016年5月31日 上午9:27:00
     */
    public static List<String[]> readTxt(InputStream inputStream, char split,
            String charset) throws Exception {
        // 声明返回对象
        List<String[]> strings = new ArrayList<String[]>();
        // 如果文件路不为空
        if (inputStream != null) {
            CsvReader reader = new CsvReader(inputStream, split,
                    Charset.forName(charset));
            // 如果有表头
            if (reader.readHeaders()) {
                // 获取文件头
                String[] headers = reader.getHeaders();
                strings.add(headers);
            }
            // 如果有记录
            while (reader.readRecord()) {
                String[] values = reader.getValues();
                strings.add(values);
            }
            reader.close();
        } else {
            throw new Exception("文件流不能为空");
        }
        return strings;
    }

    /**
     * @Description:
     * @param inputStream
     *            输入流
     * @param charset
     *            字符编码
     * @return
     * @throws Exception
     * @ReturnType List<String[]>
     * @author: shosho
     * @Created 2016 2016年5月31日 上午10:11:56
     */
    public static List<String[]> readTxt(InputStream inputStream,
            String charset) throws Exception {
        return readTxt(inputStream, Letters.SPILT, charset);
    }

    /**
     * @Description: 写文件，默认GBK
     * @param filePath
     * @param dataList
     * @throws Exception
     * @ReturnType void
     * @author: shosho
     * @Created 2016 2016年5月20日 上午11:10:58
     */
    public static void writeTxt(String filePath, List<String[]> dataList)
            throws Exception {
        // 如果文件路不为空
        if (StringUtils.isNotEmpty(filePath)) {
            CsvWriter writer = new CsvWriter(filePath, Letters.SPILT,
                    Charset.forName(Constants.Charset.GBK));
            for (String[] values : dataList) {
                writer.writeTxtRecord(values);
            }
            writer.close();
        } else {
            throw new Exception("文件路径不能为空");
        }
    }

    /**
     * @Description: 写文件
     * @param filePath
     *            文件路径
     * @param dataList
     *            数据
     * @param split
     * @param charset
     * @throws Exception
     * @ReturnType void
     * @author: shosho
     * @Created 2016 2016年5月20日 上午11:05:32
     */
    public static void writeTxt(String filePath, List<String[]> dataList,
            char split, String charset) throws Exception {
        // 如果文件路不为空
        if (StringUtils.isNotEmpty(filePath)) {
            CsvWriter writer = new CsvWriter(filePath, split,
                    Charset.forName(charset));
            for (String[] values : dataList) {
                writer.writeTxtRecord(values);
            }
            writer.close();
        } else {
            throw new Exception("文件路径不能为空");
        }
    }

    /**
     * @Description: 写txt文件
     * @param filePath
     *            文件路径（包含文件路径）
     * @param dataList
     * @param charset
     * @throws Exception
     * @ReturnType void
     * @author: shosho
     * @Created 2016 2016年5月20日 上午11:07:20
     */
    public static void writeTxt(String filePath, List<String[]> dataList,
            String charset) throws Exception {
        // 如果文件路不为空
        if (StringUtils.isNotEmpty(filePath)) {
            CsvWriter writer = new CsvWriter(filePath, Letters.SPILT,
                    Charset.forName(charset));
            for (String[] values : dataList) {
                writer.writeTxtRecord(values);
            }
            writer.close();
        } else {
            throw new Exception("文件路径不能为空");
        }
    }

    public static void main(String[] args) {

        List<String[]> dataList = new ArrayList<String[]>();

        for (int i = 0; i < 1000; i++) {
            String[] s = new String[1];
            s[0] = (int) ((Math.random() * 9 + 1) * 100000000) + "";
            dataList.add(s);
        }
        try {
            writeTxt("D:\\txt\\ivr_upload_2017102615052314333.txt", dataList,
                    Constants.Charset.UTF8);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
