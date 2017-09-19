package com.example.stevengo.myapplication.utils;

import android.content.Context;
import android.util.Log;

import com.example.stevengo.myapplication.entitys.MusicInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by StevenGo on 2017/9/13.
 * 从xml文件中读取内容的工具类
 */

public class ReadXMLUtil {
    /**读取xml文件内容*/
    public static List<MusicInfo> getMusic(Context context, String searchContent){
        //存储符合要求的音乐信息
        List<MusicInfo> list=null;
        //暂存获取到的信息
        MusicInfo music=null;
        //将内容转换成小写字母
        searchContent=new String(searchContent).toLowerCase();
        try {
            //获取XmlPullParserFactory和XmlPullParser实例
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser pullParser=factory.newPullParser();
            //指定欲读取的文件
            InputStream inputStream=context.getClass().getResourceAsStream("/assets/musics.xml");
            pullParser.setInput(inputStream,"utf-8");
            //开始
            int eventType=pullParser.getEventType();

            while(eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName=pullParser.getName();
                switch (eventType){
                    //文档开始
                    case XmlPullParser.START_DOCUMENT:
                        list=new ArrayList<>();
                        break;
                    //扫描节点
                    case XmlPullParser.START_TAG:

                        if("music".equals(nodeName)){
                            music=new MusicInfo();
                            //Log.d("StevenGo","读到music");
                        } else if ("name".equals(nodeName)) {
                            music.setName(pullParser.nextText());
                        }else if("singer".equals(nodeName)){
                            music.setSinger(pullParser.nextText());
                        }
                        break;
                    //结束节点
                    case XmlPullParser.END_TAG:
                        if("music".equals(nodeName)){
                            //Log.d("StevenGo","music.getInital:"+list.size());
                            //判断扫描到的节点的信息是否符合要求
                            if(music.getName().indexOf(searchContent)!=-1||TransformUtil.toPhonetic(music.getName()).indexOf(searchContent)!=-1||TransformUtil.toFirstPhonetic(music.getName()).indexOf(searchContent)!=-1||
                                music.getSinger().indexOf(searchContent)!=-1||TransformUtil.toPhonetic(music.getSinger()).indexOf(searchContent)!=-1||TransformUtil.toFirstPhonetic(music.getSinger()).indexOf(searchContent)!=-1){
                                //符合要求时加入链表里面，暂存器置空
                                list.add(music);
                                music=null;
                            }
                            else{
                                //不符合要求时暂存器置空
                                music=null;
                            }
                        }
                        break;
                    default:
                        break;
                }
                //手工跳到下一个节点
                eventType=pullParser.next();
            }
        } catch (XmlPullParserException e) {
            Log.d("StevenGo","XmlPullParserException异常");
        }catch (IOException e){
            Log.d("StevenGo","IOException异常");
        }
        return list;
    }
}

