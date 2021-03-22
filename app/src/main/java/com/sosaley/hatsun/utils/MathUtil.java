package com.sosaley.hatsun.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.logging.Handler;

public class MathUtil {

    public static void startBlink(Context context, TextView textView, String data) {

        textView.setText(data);

        textView.setVisibility(View.VISIBLE);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        textView.startAnimation(anim);


    }

    public static void stopBlink(Context context, TextView textView) {


        Context context1;


        textView.setVisibility(View.INVISIBLE);
        textView.clearAnimation();

    }


}
