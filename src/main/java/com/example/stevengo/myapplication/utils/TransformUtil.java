package com.example.stevengo.myapplication.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by StevenGo on 2017/9/14.
 * 将文字转换成拼音或者首字母的工具类
 */

public class TransformUtil {
    /**
     * 将字符串转化为其对应的汉语拼音
     */
    public static String toPhonetic(String content) {
        //将String类型的参数转化为字符数组
        char[] arrayText = content.toCharArray();
        //记录转化后的字符串
        String transformed = "";
        //输出格式化
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        //小写
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //以v表示字符
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            for (int i = 0; i < arrayText.length; i++) {
                //检查当前的字符是不是汉字
                if (String.valueOf(arrayText[i]).matches("[\u4e00-\u9fa5]+")) {
                    //进行转换，并将其追加transformed
                    transformed += PinyinHelper.toHanyuPinyinStringArray(arrayText[i], defaultFormat)[0];
                } else {
                    //不是汉字的时候直接追加
                    transformed += arrayText[i];
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
        }
        return transformed;
    }

    //将汉字转换成汉语拼音首字母
    public static String toFirstPhonetic(String content) {
        //将String类型的参数转化为字符数组
        char[] arrayText = content.toCharArray();
        //记录转化后的字符串
        String transformed = "";
        //输出格式化
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        //小写
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //以v表示字符
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        try {
            for (int i = 0; i < arrayText.length; i++) {
                //检查当前的字符是不是汉字
                if (String.valueOf(arrayText[i]).matches("[\u4e00-\u9fa5]+")) {
                    //进行转换，并将其追加transformed
                    transformed += PinyinHelper.toHanyuPinyinStringArray(arrayText[i], defaultFormat)[0].substring(0, 1);
                } else {
                    //不是汉字的时候直接追加
                    transformed += arrayText[i];
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
        }
        return transformed;
    }

}
