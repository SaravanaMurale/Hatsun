package com.sosaley.hatsun.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sosaley.hatsun.R;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class OTPActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    String verificationId;

    OtpTextView otpTextView;

    //https://github.com/aabhasr1/OtpView

    private long ms;


    private long mTimeLeftInMillies;

    private int minutes;
    private int seconds;

    TextView showTimer,otpResend;

    SmsVerifyCatcher smsVerifyCatcher;

    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        mAuth = FirebaseAuth.getInstance();

        Intent intent=getIntent();
        mobileNumber=intent.getStringExtra("MOBILE");

        otpTextView=(OtpTextView)findViewById(R.id.otpTextView);
        showTimer=(TextView)findViewById(R.id.showTimer);
        otpResend=(TextView)findViewById(R.id.otpResend);

        countDownTimerAtForgetPassword();

        otpResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer.setVisibility(View.VISIBLE);
                otpResend.setEnabled(false);
                countDownTimerAtForgetPassword();
            }
        });

        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                //otpTextView.setText(code);//set code in edit text
                //then you can send verification code to server

                System.out.println("SmsVerifierReceivedOTP"+code);

                otpTextView.setOTP(code);
            }
        });

        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                System.out.println("CalledType");
            }

            @Override
            public void onOTPComplete(String otp) {
                Toast.makeText(OTPActivity.this, "EnteredOTPIs " + otp,  Toast.LENGTH_SHORT).show();
            }
        });


        System.out.println("EnteredNumber "+mobileNumber);

        sendVerificationCode(mobileNumber);


    }

    private void sendVerificationCode(String mobileNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                changedCallbacks

        );


    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks changedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            System.out.println("FailedException"+e.getMessage().toString());

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;

        }


    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(OTPActivity.this, "Successfully Verified OTP", Toast.LENGTH_LONG).show();

                            String userId = mAuth.getCurrentUser().getUid();
                            String mobile = mAuth.getCurrentUser().getPhoneNumber();



                        } else {

                            System.out.println("Exception"+task.getException().getMessage().toString());

                            Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    private void countDownTimerAtForgetPassword() {

        CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                ms = millisUntilFinished;

                seconds = (int) (millisUntilFinished / 1000);
                minutes = seconds / 60;
                seconds = seconds % 60;
                showTimer.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {

                otpResend.setEnabled(true);
                showTimer.setVisibility(View.INVISIBLE);

                otpResend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        countDownTimerAtForgetPassword();

                        otpResend.setVisibility(View.INVISIBLE);
                        showTimer.setVisibility(View.VISIBLE);

                        //sendVerificationCode();
                    }
                });

                Toast.makeText(OTPActivity.this,"Timer Is Completed",Toast.LENGTH_LONG).show();

            }
        }.start();


    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

}