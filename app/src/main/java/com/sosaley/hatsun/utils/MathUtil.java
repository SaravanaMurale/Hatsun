package com.sosaley.hatsun.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.sosaley.hatsun.R;

public class MathUtil {

    public static void syncStatusBlinkStart(Context context, TextView textView, String data) {

        textView.setText(data);
        textView.setVisibility(View.VISIBLE);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        textView.startAnimation(anim);

    }

    public static void syncStatusBlinkStop(Context context, TextView textView) {

        textView.setVisibility(View.INVISIBLE);
        textView.clearAnimation();

    }

    public static void parameterStatusBlinkStart(Context context, TextView textView, String data) {

        //textView.setText(data);
        textView.setVisibility(View.VISIBLE);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        textView.startAnimation(anim);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void parameterStatusBlinkStop(Context context, TextView textView) {

        textView.setTextColor(context.getColor(R.color.black));
        //textView.setVisibility(View.INVISIBLE);
        textView.clearAnimation();

    }

}
