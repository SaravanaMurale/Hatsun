package com.sosaley.hatsun.authentication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.menu.QRDisplayActivity;
import com.sosaley.hatsun.model.BaseDTO;
import com.sosaley.hatsun.model.LoginDTO;
import com.sosaley.hatsun.model.UserDTO;
import com.sosaley.hatsun.model.UserResponseDTO;
import com.sosaley.hatsun.retrofit.ApiClient;
import com.sosaley.hatsun.retrofit.ApiInterface;
import com.sosaley.hatsun.utils.PreferencesUtil;
import com.sosaley.hatsun.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SigninActivity extends AppCompatActivity {

    TextView signInSignUp, signInForgetPassword;

    Button signInBtn;

    String forgetPasswordMobileNumber;
    EditText editTextForgetPassword;

    EditText loginMobileEditText,loginPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signInSignUp = (TextView) findViewById(R.id.signInSignUp);
        signInForgetPassword = (TextView) findViewById(R.id.signInForgetPassword);

        loginMobileEditText=(EditText)findViewById(R.id.signupMobile);
        loginPasswordEditText=(EditText)findViewById(R.id.signupPassword);

        signInBtn = (Button) findViewById(R.id.signInBtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //doLoginUser(loginMobileEditText.getText().toString(),loginPasswordEditText.getText().toString());

                doOfflineLogin();




            }
        });

        signInSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSignUpActivity();
            }
        });

        signInForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchForgetPasswordDialog();
            }
        });

    }

    private void doOfflineLogin() {

        PreferencesUtil.setValueSInt(SigninActivity.this,PreferencesUtil.ASSIGN_PROJECT,1);
        launchHomeActivity();

    }

    private void doLoginUser(String loginMobile, String loginPassword) {

        LoginDTO loginDTO=new LoginDTO(loginMobile,loginPassword);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        Call<UserResponseDTO> call=apiInterface.getLoginUserDetails(loginDTO);
        call.enqueue(new Callback<UserResponseDTO>() {
            @Override
            public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {

                UserResponseDTO userResponseDTO=response.body();

                if(userResponseDTO.getResponseCode().equals("200")){
                    PreferencesUtil.setValueSInt(SigninActivity.this,PreferencesUtil.USER_ID,userResponseDTO.getUserId());

                    launchHomeActivity();


                }else if(userResponseDTO.getResponseCode().equals("500")){
                    ToastUtil.showShortToast(SigninActivity.this,getString(R.string.not_register));
                }else if(userResponseDTO.getResponseCode().equals("700")){
                    ToastUtil.showShortToast(SigninActivity.this,getString(R.string.wrong_username));
                }
            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {

            }
        });


    }

    private void launchHomeActivity() {
        Intent intent=new Intent(SigninActivity.this,QRDisplayActivity.class);
        startActivity(intent);
        finish();

    }

    private void launchForgetPasswordDialog() {


        Button submitForget;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SigninActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_forget_password, null);

        editTextForgetPassword = (EditText) view.findViewById(R.id.forgetPasswordMobile);
        submitForget = (Button) view.findViewById(R.id.submitForgetPass);

        mBuilder.setView(view);


        submitForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPasswordMobileNumber = editTextForgetPassword.getText().toString();
                if (forgetPasswordMobileNumber != null) {
                    sendMobileNumberToServer(forgetPasswordMobileNumber);
                }

            }
        });


        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    private void sendMobileNumberToServer(final String forgetPasswordMobileNumber) {

        UserDTO mobileNumber = new UserDTO(forgetPasswordMobileNumber);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        Call<BaseDTO> call = apiInterface.sendForgetPasswordMobileNum(mobileNumber);
        call.enqueue(new Callback<BaseDTO>() {
            @Override
            public void onResponse(Call<BaseDTO> call, Response<BaseDTO> response) {

                BaseDTO baseDTO = response.body();

                if (baseDTO.getResponseCode().equals("200")) {

                    launchResetPasswordActivity(forgetPasswordMobileNumber);

                    System.out.println("AlreadyRegisterUser");
                } else if (baseDTO.getResponseCode().equals("500")) {
                    System.out.println("NewUser");
                }

            }

            @Override
            public void onFailure(Call<BaseDTO> call, Throwable t) {

                System.out.println("Exception " + t.getMessage().toString());

            }
        });


    }

    private void launchResetPasswordActivity(String forgetPasswordMobileNumber) {

        Intent intent=new Intent(SigninActivity.this,ResetPasswordActivity.class);
        intent.putExtra("MOBILENUMBER",forgetPasswordMobileNumber);
        startActivity(intent);

    }

    private void launchSignUpActivity() {

        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);

    }
}