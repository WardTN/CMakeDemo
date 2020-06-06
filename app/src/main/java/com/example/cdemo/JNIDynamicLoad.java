package com.example.cdemo;

public class JNIDynamicLoad {
    static {
        System.loadLibrary("dynamic-lib");
    }

    public native String getNativeString();
    public native int sum(int x, int y);
}
