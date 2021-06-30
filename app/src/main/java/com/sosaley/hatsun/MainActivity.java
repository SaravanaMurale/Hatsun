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


        /*{
  "issue": {
    "project_id": 1,
    "subject": "Project 1 post",
    "priority_id": 4,
    "description":"Description",
    "estimated_hours":8

  }
}*/

       String s="";

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        System.out.println("OnTouchEventIsCalled");

        System.out.println("OnTouchEvent "+event.getAction());

        return super.onTouchEvent(event);
    }
}