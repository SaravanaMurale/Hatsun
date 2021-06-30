package com.sosaley.hatsun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        System.out.println("OnTouchEventIsCalled");

        System.out.println("OnTouchEvent "+event.getAction());

        return super.onTouchEvent(event);
    }
}