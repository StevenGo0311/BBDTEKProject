package com.example.stevengo.myapplication.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.stevengo.myapplication.entitys.MusicInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by StevenGo on 2017/9/25.
 * 从网络读取数据的操作类
 */
public class InternetUtil {
    //超时时长
    public static final int CONNECTTIMEOUT=5*1000;
    /**从网络上读取数据的操作方法
     * @param kw 关键字
     * @param url url
     * @param pi 页面
     * @param pz 页大小
     * */
    public static List<MusicInfo> doGet(String url, String kw, int pi, int pz){
        //拼接url
        url=url+"?kw="+kw+"&pi="+pi+"&pz="+pz;
        //Log.d("StevenGo", "url: "+url);
        //创建List保存读取到的数据
        List<MusicInfo> musiclist=new ArrayList<>();
        MusicInfo musicInfo=null;
        try {
            //创建URL
            URL httpUrl=new URL(url);
            //建立连接
            HttpURLConnection httpURLConnection= (HttpURLConnection) httpUrl.openConnection();
            //设置超时时长为5s
            httpURLConnection.setConnectTimeout(CONNECTTIMEOUT);
            //设置请求方式为GET
            httpURLConnection.setRequestMethod("GET");
            //响应码为200,表示请求成功
            if(httpURLConnection.getResponseCode()==200) {
                //创建输入流
                InputStream inputStream = httpURLConnection.getInputStream();
                //将输入流中的数据转换为字节数组
                byte[] data = readInputStream(inputStream);
                //将字节数组转换成字符串
                String json = new String(data);
                //Log.d("StevenGo", "doGet: "+json);
                //解析得到的json数据
                JSONObject jSONObject = new JSONObject(json);
                //将读到的数据中tracks转换为JSONArray
                JSONArray jSONArray = jSONObject.getJSONArray("tracks");
                //Log.d("StevenGo", "jSONArray长度:"+jSONArray.length());
                //遍历
                for (int i = 0; i < jSONArray.length(); i++) {
                    //得到数组中的元素
                    JSONObject item = jSONArray.getJSONObject(i);
                    musicInfo = new MusicInfo();
                    //将key为title的值给name
                    musicInfo.setName(item.getString("title"));
                    //读到的musicInfo添加到list中
                    musiclist.add(musicInfo);
                }
            }
        } catch (MalformedURLException e) {
            Log.d("StevenGo","抛出MalformedURLException异常");
        } catch (IOException e) {
            Log.d("StevenGo","抛出IOException异常");
        } catch (JSONException e) {
            Log.d("StevenGo","抛出JSONException异常");
        }
        return musiclist;
    }
    /**将inputStream转换为byte[]的方法*/
    private static byte[] readInputStream(InputStream inputStream) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        //新建缓冲区域
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                bout.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭打开的流
            try {
                bout.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bout.toByteArray();
    }
}
