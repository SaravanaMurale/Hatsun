package com.sosaley.hatsun.retrofit;

import com.sosaley.hatsun.model.BaseDTO;
import com.sosaley.hatsun.model.LoginDTO;
import com.sosaley.hatsun.model.ResetPasswordDTO;
import com.sosaley.hatsun.model.UserDTO;
import com.sosaley.hatsun.model.UserResponseDTO;
import com.sosaley.hatsun.model.ValidateBatteryDTO;
import com.sosaley.hatsun.utils.AppConstant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST(AppConstant.DOMAIN+"/getuser")
    Call<UserResponseDTO> getLoginUserDetails(@Body LoginDTO loginDTO);

    @POST(AppConstant.DOMAIN+"/savenormaluser")
    Call<UserResponseDTO> saveUserRegistration(@Body UserDTO userDTO);

    @POST(AppConstant.DOMAIN+"/savegmailuser")
    Call<UserResponseDTO> saveGmailUser(@Body UserDTO userDTO);

    @POST(AppConstant.DOMAIN+"/")
    Call<ResponseBody> saveFBUser(@Body UserDTO userDTO);

    @POST(AppConstant.DOMAIN+"/forgetpassword")
    Call<BaseDTO> sendForgetPasswordMobileNum(@Body UserDTO userDTO);

    @POST(AppConstant.DOMAIN+"/resetpassword")
    Call<UserResponseDTO> resetPassword(@Body ResetPasswordDTO resetPasswordDTO);

    @POST(AppConstant.DOMAIN+"/validatedata")
    Call<BaseDTO> syncScanDataWithServer(@Body ValidateBatteryDTO validateBatteryDTO);


}
