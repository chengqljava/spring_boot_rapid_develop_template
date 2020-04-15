package com.hwsafe.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 项目名称：TODO 类名称：PinYin4jUtil 类描述：TODO
 *
 * @author Xucy 创建时间：2019年01月15日 14:22
 * @author modify Xucy 修改时间：2019年01月15日 14:22 修改备注：TODO
 */
public class PinYin4jUtil {

    public enum PinYinType {
        F_Q_O_F, // 第一个汉字全拼 其他首字母
        Q_Q, // 全部全拼
        Q_F// 全部首字符
    }

    public static String getPinyin(String name, PinYinType pinYinType) {
        StringBuilder pinYinName = new StringBuilder();
        if (name == null || name.length() <= 0) {
            return pinYinName.toString();
        }
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        char[] values = name.toCharArray();
        switch (pinYinType) {
        case F_Q_O_F:
            try {

                for (int i = 0; i < values.length; i++) {
                    if (i == 0) {
                        if (values[0] > 128) {
                            pinYinName.append(
                                    PinyinHelper.toHanyuPinyinStringArray(
                                            values[i], defaultFormat)[0]);
                        } else {
                            pinYinName.append(values[i]);
                        }
                    } else {
                        if (values[i] > 128) {
                            pinYinName.append(
                                    PinyinHelper.toHanyuPinyinStringArray(
                                            values[i], defaultFormat)[0]
                                                    .charAt(0));
                        } else {
                            pinYinName.append(values[i]);
                        }
                    }
                }

            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            break;
        case Q_Q:
            try {

                for (int i = 0; i < values.length; i++) {
                    if (values[0] > 128) {
                        pinYinName.append(PinyinHelper.toHanyuPinyinStringArray(
                                values[i], defaultFormat)[0]);
                    } else {
                        pinYinName.append(values[i]);
                    }
                }

            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            break;
        case Q_F:
            try {

                for (int i = 0; i < values.length; i++) {
                    if (values[i] > 128) {
                        pinYinName.append(
                                PinyinHelper.toHanyuPinyinStringArray(values[i],
                                        defaultFormat)[0].charAt(0));
                    } else {
                        pinYinName.append(values[i]);
                    }
                }

            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            break;
        }
        return pinYinName.toString();
    }

    public static void main(String[] args) {
        final String pinyin = PinYin4jUtil.getPinyin("徐长友", PinYinType.Q_Q);
        final String pinyin1 = PinYin4jUtil.getPinyin("徐长友",
                PinYinType.F_Q_O_F);
        final String pinyin2 = PinYin4jUtil.getPinyin("徐长友", PinYinType.Q_F);
        System.out.println(pinyin);
        System.out.println(pinyin1);
        System.out.println(pinyin2);
    }
}
