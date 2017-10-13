package com.example.stevengo.myapplication.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by StevenGo on 2017/10/12.
 * 自定义工厂，用FastJson实现
 */
public class FastJsonConverterFactory extends Converter.Factory{

    public static FastJsonConverterFactory create() {
        return new FastJsonConverterFactory();
    }

    /**
     * 该方法用来转换服务器返回数据
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastJsonResponseBodyConverter<>(type);
    }

    /**
     * 该方法用来转换发送给服务器的数据
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastJsonRequestBodyConverter<>();
    }

}
