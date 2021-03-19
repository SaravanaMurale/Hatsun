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
import com.sosaley.hatsun.model.UserDTO;
import com.sosaley.hatsun.retrofit.ApiClient;
import com.sosaley.hatsun.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SigninActivity extends AppCompatActivity {

    TextView signInSignUp, signInForgetPassword;

    Button signInBtn;

    String forgetPasswordMobileNumber;
    EditText editTextForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signInSignUp = (TextView) findViewById(R.id.signInSignUp);
        signInForgetPassword = (TextView) findViewById(R.id.signInForgetPassword);

        signInBtn = (Button) findViewById(R.id.signInBtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(SigninActivity.this, QRDisplayActivity.class);
                startActivity(intent);


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