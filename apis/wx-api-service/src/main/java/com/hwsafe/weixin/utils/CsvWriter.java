/*
 * Java CSV is a stream based library for reading and writing
 * CSV and other delimited data.
 *   
 * Copyright (C) Bruce Dunwiddie bruce@csvreader.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */
package com.hwsafe.weixin.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * A stream based writer for writing delimited text data to a file or a stream.
 */
public class CsvWriter {
    /**
     * Double up the text qualifier to represent an occurrence of the text
     * qualifier.
     */
    public static final int ESCAPE_MODE_DOUBLED = 1;

    /**
     * Use a backslash character before the text qualifier to represent an
     * occurrence of the text qualifier.
     */
    public static final int ESCAPE_MODE_BACKSLASH = 2;

    private Writer outputStream = null;

    private String fileName = null;

    private boolean firstColumn = true;

    private boolean useCustomRecordDelimiter = false;

    private Charset charset = null;

    // this holds all the values for switches that the user is allowed to set
    private UserSettings userSettings = new UserSettings();

    private boolean initialized = false;

    private boolean closed = false;

    private String systemRecordDelimiter = System.getProperty("line.separator");

    private class UserSettings {
        // having these as publicly accessible members will prevent
        // the overhead of the method call that exists on properties
        public char TextQualifier;

        public boolean UseTextQualifier;

        public char Delimiter;

        public char RecordDelimiter;

        public char Comment;

        public int EscapeMode;

        public boolean ForceQualifier;

        public UserSettings() {
            TextQualifier = Constants.Letters.QUOTE;
            UseTextQualifier = true;
            Delimiter = Constants.Letters.COMMA;
            RecordDelimiter = Constants.Letters.NULL;
            Comment = Constants.Letters.POUND;
            EscapeMode = ESCAPE_MODE_DOUBLED;
            ForceQualifier = false;
        }
    }

    public static String replace(String original, String pattern,
            String replace) {
        final int len = pattern.length();
        int found = original.indexOf(pattern);

        if (found > -1) {
            StringBuffer sb = new StringBuffer();
            int start = 0;

            while (found != -1) {
                sb.append(original.substring(start, found));
                sb.append(replace);
                start = found + len;
                found = original.indexOf(pattern, start);
            }

            sb.append(original.substring(start));

            return sb.toString();
        } else {
            return original;
        }
    }

    /**
     * Creates a {@link com.cmos.csap.common.csv.rd.csvreader.CsvWriter
     * CsvWriter} object using an OutputStream to write data to.
     * 
     * @param outputStream
     *            The stream to write the column delimited data to.
     * @param delimiter
     *            The character to use as the column delimiter.
     * @param charset
     *            The {@link java.nio.charset.Charset Charset} to use while
     *            writing the data.
     */
    public CsvWriter(OutputStream outputStream, char delimiter,
            Charset charset) {
        this(new OutputStreamWriter(outputStream, charset), delimiter);
    }

    /**
     * Creates a {@link com.cmos.csap.common.csv.rd.csvreader.CsvWriter
     * CsvWriter} object using a file as the data destination.&nbsp;Uses a comma
     * as the column delimiter and ISO-8859-1 as the
     * {@link java.nio.charset.Charset Charset}.
     * 
     * @param fileName
     *            The path to the file to output the data.
     */
    public CsvWriter(String fileName) {
        this(fileName, Constants.Letters.COMMA, Charset.forName("ISO-8859-1"));
    }

    /**
     * Creates a {@link com.cmos.csap.common.csv.rd.csvreader.CsvWriter
     * CsvWriter} object using a file as the data destination.
     * 
     * @param fileName
     *            The path to the file to output the data.
     * @param delimiter
     *            The character to use as the column delimiter.
     * @param charset
     *            The {@link java.nio.charset.Charset Charset} to use while
     *            writing the data.
     */
    public CsvWriter(String fileName, char delimiter, Charset charset) {
        if (fileName == null) {
            throw new IllegalArgumentException(
                    "Parameter fileName can not be null.");
        }

        if (charset == null) {
            throw new IllegalArgumentException(
                    "Parameter charset can not be null.");
        }

        this.fileName = fileName;
        userSettings.Delimiter = delimiter;
        this.charset = charset;
    }

    /**
     * Creates a {@link com.cmos.csap.common.csv.rd.csvreader.CsvWriter
     * CsvWriter} object using a Writer to write data to.
     * 
     * @param outputStream
     *            The stream to write the column delimited data to.
     * @param delimiter
     *            The character to use as the column delimiter.
     */
    public CsvWriter(Writer outputStream, char delimiter) {
        if (outputStream == null) {
            throw new IllegalArgumentException(
                    "Parameter outputStream can not be null.");
        }

        this.outputStream = outputStream;
        userSettings.Delimiter = delimiter;
        initialized = true;
    }

    /**
     * 
     */
    private void checkClosed() throws IOException {
        if (closed) {
            throw new IOException(
                    "This instance of the CsvWriter class has already been closed.");
        }
    }

    /**
     * 
     */
    private void checkInit() throws IOException {
        if (!initialized) {
            if (fileName != null) {
                outputStream = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fileName), charset));
            }

            initialized = true;
        }
    }

    /**
     * Closes and releases all related resources.
     */
    public void close() {
        if (!closed) {
            close(true);

            closed = true;
        }
    }

    /**
     * 
     */
    private void close(boolean closing) {
        if (!closed) {
            if (closing) {
                charset = null;
            }

            try {
                if (initialized) {
                    outputStream.close();
                }
            } catch (Exception e) {
                // just eat the exception
            }

            outputStream = null;

            closed = true;
        }
    }

    /**
     * Ends the current record by sending the record delimiter.
     * 
     * @exception IOException
     *                Thrown if an error occurs while writing data to the
     *                destination stream.
     */
    public void endRecord() throws IOException {
        checkClosed();

        checkInit();

        if (useCustomRecordDelimiter) {
            outputStream.write(userSettings.RecordDelimiter);
        } else {
            outputStream.write(systemRecordDelimiter);
        }

        firstColumn = true;
    }

    /**
     * 
     */
    @Override
    protected void finalize() {
        close(false);
    }

    /**
     * Clears all buffers for the current writer and causes any buffered data to
     * be written to the underlying device.
     * 
     * @exception IOException
     *                Thrown if an error occurs while writing data to the
     *                destination stream.
     */
    public void flush() throws IOException {
        outputStream.flush();
    }

    public char getComment() {
        return userSettings.Comment;
    }

    /**
     * Gets the character being used as the column delimiter.
     * 
     * @return The character being used as the column delimiter.
     */
    public char getDelimiter() {
        return userSettings.Delimiter;
    }

    public int getEscapeMode() {
        return userSettings.EscapeMode;
    }

    /**
     * Whether fields will be surrounded by the text qualifier even if the
     * qualifier is not necessarily needed to escape this field.
     * 
     * @return Whether fields will be forced to be qualified or not.
     */
    public boolean getForceQualifier() {
        return userSettings.ForceQualifier;
    }

    public char getRecordDelimiter() {
        return userSettings.RecordDelimiter;
    }

    /**
     * Gets the character to use as a text qualifier in the data.
     * 
     * @return The character to use as a text qualifier in the data.
     */
    public char getTextQualifier() {
        return userSettings.TextQualifier;
    }

    /**
     * Whether text qualifiers will be used while writing data or not.
     * 
     * @return Whether text qualifiers will be used while writing data or not.
     */
    public boolean getUseTextQualifier() {
        return userSettings.UseTextQualifier;
    }

    /**
     * 
     * @Description: 验证是否是数字
     * @param str
     * @return
     * @ReturnType boolean
     * @author: xueshuailing
     * @Created 2016 2016年7月6日 上午9:16:53
     */
    private boolean isNumeric(String str) {
        if (StringUtils.isBlank(str)) {// 如果是null 或者"" 返回false
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.trim());
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public void setComment(char comment) {
        userSettings.Comment = comment;
    }

    /**
     * Sets the character to use as the column delimiter.
     * 
     * @param delimiter
     *            The character to use as the column delimiter.
     */
    public void setDelimiter(char delimiter) {
        userSettings.Delimiter = delimiter;
    }

    public void setEscapeMode(int escapeMode) {
        userSettings.EscapeMode = escapeMode;
    }

    /**
     * Use this to force all fields to be surrounded by the text qualifier even
     * if the qualifier is not necessarily needed to escape this field. Default
     * is false.
     * 
     * @param forceQualifier
     *            Whether to force the fields to be qualified or not.
     */
    public void setForceQualifier(boolean forceQualifier) {
        userSettings.ForceQualifier = forceQualifier;
    }

    /**
     * Sets the character to use as the record delimiter.
     * 
     * @param recordDelimiter
     *            The character to use as the record delimiter. Default is
     *            combination of standard end of line characters for Windows,
     *            Unix, or Mac.
     */
    public void setRecordDelimiter(char recordDelimiter) {
        useCustomRecordDelimiter = true;
        userSettings.RecordDelimiter = recordDelimiter;
    }

    /**
     * Sets the character to use as a text qualifier in the data.
     * 
     * @param textQualifier
     *            The character to use as a text qualifier in the data.
     */
    public void setTextQualifier(char textQualifier) {
        userSettings.TextQualifier = textQualifier;
    }

    /**
     * Sets whether text qualifiers will be used while writing data or not.
     * 
     * @param useTextQualifier
     *            Whether to use a text qualifier while writing data or not.
     */
    public void setUseTextQualifier(boolean useTextQualifier) {
        userSettings.UseTextQualifier = useTextQualifier;
    }

    /**
     * Writes another column of data to this record.&nbsp;Does not preserve
     * leading and trailing whitespace in this column of data.
     * 
     * @param content
     *            The data for the new column.
     * @exception IOException
     *                Thrown if an error occurs while writing data to the
     *                destination stream.
     */
    public void write(String content) throws IOException {
        write(content, false);
    }

    /**
     * Writes another column of data to this record.
     * 
     * @param content
     *            The data for the new column.
     * @param preserveSpaces
     *            Whether to preserve leading and trailing whitespace in this
     *            column of data.
     * @exception IOException
     *                Thrown if an error occurs while writing data to the
     *                destination stream.
     */
    public void write(String content, boolean preserveSpaces)
            throws IOException {
        checkClosed();

        checkInit();

        if (content == null) {
            content = "";
        }
        if (!firstColumn) {
            outputStream.write(userSettings.Delimiter);
        }
        boolean textQualify = userSettings.ForceQualifier;
        if (!textQualify && userSettings.UseTextQualifier && (content
                .indexOf(userSettings.TextQualifier) > -1
                || content.indexOf(userSettings.Delimiter) > -1
                || (!useCustomRecordDelimiter
                        && (content.indexOf(Constants.Letters.LF) > -1
                                || content.indexOf(Constants.Letters.CR) > -1))
                || (useCustomRecordDelimiter
                        && content.indexOf(userSettings.RecordDelimiter) > -1)
                || (firstColumn && content.length() > 0
                        && content.charAt(0) == userSettings.Comment)
                ||
                // check for empty first column, which if on its own
                // line must
                // be qualified or the line will be skipped
                (firstColumn && content.length() == 0))) {
            textQualify = true;
        }

        if (userSettings.UseTextQualifier && !textQualify
                && content.length() > 0 && preserveSpaces) {
            char firstLetter = content.charAt(0);

            if (firstLetter == Constants.Letters.SPACE
                    || firstLetter == Constants.Letters.TAB) {
                textQualify = true;
            }

            if (!textQualify && content.length() > 1) {
                char lastLetter = content.charAt(content.length() - 1);

                if (lastLetter == Constants.Letters.SPACE
                        || lastLetter == Constants.Letters.TAB) {
                    textQualify = true;
                }
            }
        }

        if (textQualify) {
            outputStream.write(userSettings.TextQualifier);

            if (userSettings.EscapeMode == ESCAPE_MODE_BACKSLASH) {
                content = replace(content, "" + Constants.Letters.BACKSLASH,
                        "" + Constants.Letters.BACKSLASH
                                + Constants.Letters.BACKSLASH);
                content = replace(content, "" + userSettings.TextQualifier,
                        "" + Constants.Letters.BACKSLASH
                                + userSettings.TextQualifier);
            } else {
                content = replace(content, "" + userSettings.TextQualifier,
                        "" + userSettings.TextQualifier
                                + userSettings.TextQualifier);
            }
        } else if (userSettings.EscapeMode == ESCAPE_MODE_BACKSLASH) {
            content = replace(content, "" + Constants.Letters.BACKSLASH,
                    "" + Constants.Letters.BACKSLASH
                            + Constants.Letters.BACKSLASH);
            content = replace(content, "" + userSettings.Delimiter,
                    "" + Constants.Letters.BACKSLASH + userSettings.Delimiter);

            if (useCustomRecordDelimiter) {
                content = replace(content, "" + userSettings.RecordDelimiter,
                        "" + Constants.Letters.BACKSLASH
                                + userSettings.RecordDelimiter);
            } else {
                content = replace(content, "" + Constants.Letters.CR, ""
                        + Constants.Letters.BACKSLASH + Constants.Letters.CR);
                content = replace(content, "" + Constants.Letters.LF, ""
                        + Constants.Letters.BACKSLASH + Constants.Letters.LF);
            }

            if (firstColumn && content.length() > 0
                    && content.charAt(0) == userSettings.Comment) {
                if (content.length() > 1) {
                    content = "" + Constants.Letters.BACKSLASH
                            + userSettings.Comment + content.substring(1);
                } else {
                    content = "" + Constants.Letters.BACKSLASH
                            + userSettings.Comment;
                }
            }
        }

        outputStream.write(content);

        if (textQualify) {
            outputStream.write(userSettings.TextQualifier);
        }

        firstColumn = false;
    }

    public void writeComment(String commentText) throws IOException {
        checkClosed();

        checkInit();

        outputStream.write(userSettings.Comment);

        outputStream.write(commentText);

        if (useCustomRecordDelimiter) {
            outputStream.write(userSettings.RecordDelimiter);
        } else {
            outputStream.write(systemRecordDelimiter);
        }

        firstColumn = true;
    }

    /**
     * Writes a new record using the passed in array of values.
     * 
     * @param values
     *            Values to be written.
     * 
     * @throws IOException
     *             Thrown if an error occurs while writing data to the
     *             destination stream.
     */
    public void writeRecord(String[] values) throws IOException {
        writeRecord(values, false);
    }

    public void writeTxtRecord(String[] values) throws IOException {
        writeTxtRecord(values, false);
    }

    /**
     * Writes a new record using the passed in array of values.
     * 
     * @param values
     *            Values to be written.
     * 
     * @param preserveSpaces
     *            Whether to preserver leading and trailing spaces in columns
     *            while writing out to the record or not.
     * 
     * @throws IOException
     *             Thrown if an error occurs while writing data to the
     *             destination stream.
     */
    public void writeRecord(String[] values, boolean preserveSpaces)
            throws IOException {
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                String content = values[i];
                if (isNumeric(content) && content.length() > 11) {// 是数字
                                                                  // 并且长度大于11
                    content = "'" + content;
                }
                write(content, preserveSpaces);
            }
            endRecord();
        }
    }

    /**
     * 
     * @Description: txt写文件 不对数字进行加' 格式化 并把“|”替换成 “”
     * @param values
     * @param preserveSpaces
     * @throws IOException
     * @ReturnType void
     * @author: xueshuailing
     * @Created 2016 2016年7月19日 下午2:07:23
     */
    public void writeTxtRecord(String[] values, boolean preserveSpaces)
            throws IOException {
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {// |进行转移 否则替换不了
                write(replaceBlank(values[i].toString().replaceAll("\\|", "")),
                        preserveSpaces);
            }
            endRecord();
        }
    }

    /**
     * 
     * @Description: 去掉字符串中的回车换行空格
     * @param str
     * @return
     * @ReturnType String
     * @author: xueshuailing
     * @Created 2016 2016年8月1日 下午3:09:51
     */
    private static String replaceBlank(String str) {
        String dest = "";// 定义返回的字符串
        if (StringUtils.isNotBlank(str)) {// 如果传过来的字符串不为空
            Pattern p = Pattern.compile("\r|\n");// 正则表达式 去掉回车换行空格
            Matcher m = p.matcher(str);// 匹配字符串
            dest = m.replaceAll("");// 替换成“”
        }
        return dest;// 返回最终字符串
    }
}