package com.example.stevengo.myapplication.services;

import android.content.Context;
import android.util.Log;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StevenGo on 2017/9/15.
 * 提供操作搜索历史的服务
 */
public class DoHistoryServices {
    //文件名
    final String FILE_NAME="searchHistory.bin";
    /**记录读取到的搜索历史*/
    private List<String> historyText;

    /**输入输出流*/
    private OutputStream outputStream;
    private InputStream inputStream;

    /**输入输出转换流*/
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;

    /**缓冲流*/
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    /**接收调用者传过来的Context*/
    private Context mContext;
    /**构造方法*/
    public DoHistoryServices(Context context){
        historyText=new ArrayList<>();
        mContext=context;
    }
    /**从文件里读取搜索历史*/
    public List<String> readHistory(){
        try{
            //当文件不存在的时候创建文件
            outputStream=mContext.openFileOutput(FILE_NAME,Context.MODE_APPEND);
            //创建输入流，转换流和缓冲流
            inputStream=mContext.openFileInput(FILE_NAME);
            inputStreamReader=new InputStreamReader(inputStream);
            bufferedReader=new BufferedReader(inputStreamReader);
            //清除list里的内容
            historyText.clear();
            String temp="";
            //从文件开头逐行读取文件内容，暂存到temp里
            while((temp=bufferedReader.readLine())!=null){
                //检查当前的文本是不是已经搜索过了
                //如果已经搜索过设置标志位为false.
                boolean flag=true;
                for(int i=0;i<historyText.size();i++){
                    if(temp.equals(historyText.get(i))){
                        flag=false;
                        //如果已经有重复，停止检索
                        break;
                    }
                }
                //判断读到的是不是空行，如果不是空行，将其加到list中
                if(!temp.equals("")&&flag){
                    historyText.add(temp);
                }
            }
        }catch (FileNotFoundException e){

        }catch (IOException e){

        }finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //如果长度超出10个，只输出最后10个
        if(historyText.size()>10){
            historyText=historyText.subList(historyText.size()-10,historyText.size());
        }
//        for(int i=0;i<historyText.size();i++){
//            Log.d("StevenGo",historyText.get(i));
//        }
        return historyText;
    }
    /**向文件中写入搜索历史*/
    public void writeHistory(String historyText){
        try{
            //创建输出流，转换流和缓冲流
            outputStream=mContext.openFileOutput(FILE_NAME,Context.MODE_APPEND);
            outputStreamWriter=new OutputStreamWriter(outputStream);
            bufferedWriter=new BufferedWriter(outputStreamWriter);
            //首先写入一个换行符
            bufferedWriter.write("\n");
            //写入数据
            bufferedWriter.write(historyText);
        }catch(FileNotFoundException e){

        }catch (IOException e){

        }finally {
            try{
                bufferedWriter.close();
                outputStreamWriter.close();
                outputStream.close();

            }catch (IOException e){

            }
        }
    }
    /**清除搜索记录*/
    public void clearHistory(){
        //删除文件
        mContext.deleteFile(FILE_NAME);
    }
}
