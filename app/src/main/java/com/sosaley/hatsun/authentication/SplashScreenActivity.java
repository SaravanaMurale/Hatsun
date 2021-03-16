package com.sosaley.hatsun.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.sosaley.hatsun.R;


public class SplashScreenActivity extends AppCompatActivity {


    /*Gmail SignUp Working
sosyhapiot@gmail.com
sosyhapiot@123*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*lazyLoader=findViewById(R.id.myLoader);

        TashieLoader tashie = new TashieLoader(
                this, 5,
                30, 10,
                ContextCompat.getColor(this, R.color.green));

        tashie.setAnimDuration(500);
        tashie.setAnimDelay(100);
        tashie.setInterpolator(new LinearInterpolator());

        lazyLoader.addView(tashie);*/

        new SplashDownCountDown(1500, 1000).start();

    }


    private class SplashDownCountDown extends CountDownTimer {

        SplashDownCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);


        }

        @Override
        public void onTick(long milliSecond) {

        }

        @Override
        public void onFinish() {

            Intent intent=new Intent(SplashScreenActivity.this,SigninActivity.class);
            startActivity(intent);
            finish();



        /*    Intent intent;

            userId = PreferencesUtil.getValueInt(SplashScreenActivity.this, PreferencesUtil.USER_ID);

            if (userId > 0) {
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }*/
        }
    }
}