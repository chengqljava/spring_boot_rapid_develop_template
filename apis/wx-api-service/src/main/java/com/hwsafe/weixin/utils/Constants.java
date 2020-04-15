
package com.hwsafe.weixin.utils;

/**
 * @Description: 静态常量类
 * @ClassName Constants
 * @author: shosho
 * @Created 2016 2016年5月20日 上午9:16:55
 */
public class Constants {

    // 07格式的excel最大行数
    public static final int ROW_07_MAX_SIZE = 1048575;
    // 03格式的最大行数
    public static final int ROW_03_MAX_SIZE = 65535;

    // 字符编码
    public static class Charset {
        // GBK编码
        public static final String GBK = "GBK";
        // GB2312
        public static final String GB2312 = "GB2312";
        // UTF编码
        public static final String UTF8 = "UTF-8";
        // ISO-8859-1
        public static final String ISO88591 = "ISO-8859-1";
    }

    // 字符
    public static class Letters {
        public static final char LF = '\n';

        public static final char CR = '\r';

        public static final char QUOTE = '"';

        public static final char COMMA = ',';

        public static final char SPACE = ' ';

        public static final char TAB = '\t';

        public static final char POUND = '#';

        public static final char BACKSLASH = '\\';

        public static final char NULL = '\0';

        public static final char BACKSPACE = '\b';

        public static final char FORM_FEED = '\f';

        public static final char ESCAPE = '\u001B'; // ASCII/ANSI escape

        public static final char VERTICAL_TAB = '\u000B';

        public static final char ALERT = '\u0007';

        public static final char SPILT = '|';
    }

}
