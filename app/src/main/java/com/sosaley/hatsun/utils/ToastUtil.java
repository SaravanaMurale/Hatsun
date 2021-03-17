package com.sosaley.hatsun.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    /*static Context sContext;
    static ToastUtil toastUtils;

    public ToastUtil() {
    }


    public static ToastUtil getInstance(Context context){

        if (toastUtils == null) {
            toastUtils = new ToastUtil();
            sContext = context;
        }
        return toastUtils;

    }*/

    public static void showLongToast(Context sContext,String message){

        Toast.makeText(sContext,message,Toast.LENGTH_LONG).show();

    }

    public static void showShortToast(Context sContext,String message){
        Toast.makeText(sContext,message,Toast.LENGTH_SHORT).show();
    }

}
