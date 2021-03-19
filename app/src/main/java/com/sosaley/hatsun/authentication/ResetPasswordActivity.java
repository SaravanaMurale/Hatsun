package com.sosaley.hatsun.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.utils.ToastUtil;

import static com.sosaley.hatsun.utils.Validation.validatePassword;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText resetPassword, resetConfirmPassword;
    Button btnReset;

    String resetMobileNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intent = getIntent();
        resetMobileNum = intent.getStringExtra("MOBILENUMBER");

        resetPassword = findViewById(R.id.resetPassword);
        resetConfirmPassword = findViewById(R.id.resetConfirmPassword);
        btnReset = findViewById(R.id.btnResetSubmit);


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String set_Password = resetPassword.getText().toString().trim();
                String resetConfirm_Password=resetConfirmPassword.getText().toString().trim();

                doValidatePassword(set_Password,resetConfirm_Password);

            }
        });


    }

    private void doValidatePassword(String set_password, String resetConfirm_password) {

        if (set_password.isEmpty() || set_password.equals("") || set_password.equals(null)) {
            Toast.makeText(ResetPasswordActivity.this, "Please Enter Password", Toast.LENGTH_LONG).show();
            ToastUtil.showShortToast(ResetPasswordActivity.this,getString(R.string.enter_password));
            resetPassword.findFocus();
            return;
        }

        if (resetConfirm_password.isEmpty() || resetConfirm_password.equals("") || resetConfirm_password.equals(null)) {
            ToastUtil.showShortToast(ResetPasswordActivity.this,getString(R.string.enter_confirm_password));
            resetPassword.findFocus();
            return;
        }

        if (!validatePassword(set_password)) {
            Toast.makeText(ResetPasswordActivity.this, "Password should have minimum 6 characters", Toast.LENGTH_LONG).show();
            resetPassword.findFocus();
            return;
        }

        if(!set_password.equals(resetConfirm_password)){
            Toast.makeText(ResetPasswordActivity.this, "Password and Confirm Password Mismatch", Toast.LENGTH_LONG).show();
            return;
        }
        if(set_password.equals(resetConfirm_password)){

            resetPasswordInServer(set_password);

        }



    }

    private void resetPasswordInServer(String set_password) {



    }
}