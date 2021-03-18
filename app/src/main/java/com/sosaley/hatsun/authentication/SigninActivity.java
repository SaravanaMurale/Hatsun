package com.sosaley.hatsun.authentication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.menu.QRDisplayActivity;


public class SigninActivity extends AppCompatActivity {

    TextView signInSignUp,signInForgetPassword;

    Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signInSignUp=(TextView)findViewById(R.id.signInSignUp);
        signInForgetPassword=(TextView)findViewById(R.id.signInForgetPassword);

        signInBtn=(Button)findViewById(R.id.signInBtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(SigninActivity.this, QRDisplayActivity.class);
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

        EditText editTextForgetPassword;
        Button submitForget,cancelForget;

        AlertDialog.Builder mBuilder=new AlertDialog.Builder(SigninActivity.this);
        View mView=getLayoutInflater().inflate(R.layout.layout_forget_password,null);

        editTextForgetPassword = (EditText) mView.findViewById(R.id.forgetPasswordMobile);
        submitForget=(Button)mView.findViewById(R.id.submitForgetPass);
        cancelForget=(Button)mView.findViewById(R.id.cancelforgetPass);

        String forgetPasswordMobileNumber=editTextForgetPassword.getText().toString();
        submitForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        mBuilder.setView(mView);
        AlertDialog alertDialog=mBuilder.create();
        alertDialog.show();
    }

    private void launchSignUpActivity() {

        Intent intent=new Intent(SigninActivity.this,SignupActivity.class);
        startActivity(intent);

    }
}