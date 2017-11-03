package com.example.stevengo.myapplication.utils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by StevenGo on 2017/10/12.
 * 自定义ConVerter类，实现用FastJson转换
 */
public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    /**
     * 定义数据类型
     */
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
    }
}
