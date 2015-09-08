package com.example.wudongchuan.myapplication;

/**
 * Created by wudongchuan on 2015/6/26.
 */
/**
 * Created by linc on 15-3-29.
 */
public class SecondLib {
    // Native implementation
    static {
        System.loadLibrary("SecondLib");
    }
    //int array
    public static native int[] intMethod();
    //string array
    public static native String[] stringMethod();

    public static native  int sumMethod(int a,int b);
}