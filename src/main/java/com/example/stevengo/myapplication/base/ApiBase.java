package com.example.stevengo.myapplication.base;

import com.example.stevengo.myapplication.entitys.LocationInfo;
import com.example.stevengo.myapplication.entitys.MusicEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by StevenGo on 2017/9/30.
 * 访问api的接口
 */

public interface ApiBase {
   /**采用GET注解，字符串的内容为相对路径*/
    @GET(UrlConsTable.URL_SAS)
    /**声明从网络读取数据的方法*/
    Call<MusicEntity> getMusic(@Query("kw")String music_kw,@Query("pi")int music_pi,@Query("pz")int music_pz);

    @GET(UrlConsTable.LOCATION_URL_VGG)
    Call<LocationInfo> getLocation(@Query("key")String location_key,@Query("location") String location_coordinate);

    @GET(UrlConsTable.LOCATION_URL_VGG)
    Call<ResponseBody> getLocationResposeBody(@Query("key")String location_key, @Query("location") String location_coordinate);

}
