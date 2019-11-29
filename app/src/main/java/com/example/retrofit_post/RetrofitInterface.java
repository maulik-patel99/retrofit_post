package com.example.retrofit_post;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface RetrofitInterface
{
    @POST("test_post.php")
    @FormUrlEncoded
    Call<String> data(@Field("u_name")String name);



}
