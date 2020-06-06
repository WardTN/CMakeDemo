package com.example.cdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String name;
    private static int num;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Example of a call to a native method
//        TextView tv = findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        final JNIDynamicLoad jniDynamicLoad = new JNIDynamicLoad();
        final TextView textView = findViewById(R.id.sample_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(jniDynamicLoad.getNativeString());
                String str = callNativeStr("java string");
                Log.d("CHEN", str);
                stringMethod("java string");

                String[] strings = {"apple", "pear", "banana"};
                Log.d("CHEN", "the first item of strings: " + callNativeStrArray(strings));

                accessInstanceField();  //C++ 调用 Java 变量
                Log.d("CHEN", "access instance field and change name to:" + name);
                accessStaticField();    //C++调用Java 静态变量
                Log.d("CHEN", "access static field and change num to:" + num);

                accessInstanceMethod(); //C++调用java方法
                accessStaticMethod();   //C ++ 调用java静态方法

                //两种方法创建对象
                Log.d("CHEN", invokeConstructors().getName());
                Log.d("CHEN", allocConstructors().getName());

            }
        });
    }

    public void callInstanceMethod(int num) {
        Log.e("CHEN", "instance method invoked in MainActivity,and pass param: " + num);
    }

    public static void callStaticMethod(String str, int num) {
        Log.d("CHEN", "static method invoked in MainActivity,and pass param:" + str + " and " + num);
    }

    public static void callStaticMethod(String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            Log.d("affy", i + " static method invoked in MainActivity,and pass param:" + strs[i]);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String callNativeStr(String str);

    public native void stringMethod(String str);

    public native String callNativeStrArray(String[] strArray);

    public native void accessInstanceField();

    public native void accessStaticField();

    public native void accessInstanceMethod();

    public native void accessStaticMethod();

    public native Animal invokeConstructors();

    public native Animal allocConstructors();

}
