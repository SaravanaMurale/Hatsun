package com.sosaley.hatsun.retrofit;

import com.sosaley.hatsun.model.BaseDTO;
import com.sosaley.hatsun.model.GetUserDTO;
import com.sosaley.hatsun.model.IssuePostDTO;
import com.sosaley.hatsun.model.LoginDTO;
import com.sosaley.hatsun.model.ResetPasswordDTO;
import com.sosaley.hatsun.model.UserDTO;
import com.sosaley.hatsun.model.UserResponseDTO;
import com.sosaley.hatsun.model.ValidateBatteryDTO;
import com.sosaley.hatsun.utils.AppConstant;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
    Call<ValidateBatteryDTO> syncScanDataWithServer(@Body ValidateBatteryDTO validateBatteryDTO);

    @POST(AppConstant.MAIL_DOMAIN+"/sendMail")
    Call<BaseDTO> sendEditedValueToServer(@Body ValidateBatteryDTO validateBatteryDTO);

    @POST(AppConstant.DOMAIN+"/")
    Call<UserResponseDTO> getUserDetails(@Body GetUserDTO getUserDTO);

    @Headers({"Content-Type:application/json"})
    @POST(AppConstant.LOCAL_SERVER_ISSUE_POST+"issues.json")
    Call<BaseDTO> postIssue(@Header("Authorization") String token,@Header("Authorization") String token1,  @Body List<IssuePostDTO> issuePostList);

    @GET(AppConstant.LOCAL_SERVER+"current.json")
    Call<BaseDTO> getUserDetails();

    @GET(AppConstant.LOCAL_SERVER+"current.json")
    Call<BaseDTO> getUserDetailsWithUserAndPass(@Header("Authorization") String auth);
}
