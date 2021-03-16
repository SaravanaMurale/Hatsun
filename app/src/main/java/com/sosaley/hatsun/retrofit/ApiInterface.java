package com.sosaley.hatsun.retrofit;

import com.sosaley.hatsun.model.UserDTO;
import com.sosaley.hatsun.utils.AppConstant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST(AppConstant.DOMAIN+"/saveuser")
    Call<ResponseBody> saveUserRegistration(@Body UserDTO userDTO);


}
