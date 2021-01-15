package com.wxyy.shiro_demo.shiro;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream(new File("E:\\学习2018-05-31\\geektime\\java_进阶训练营\\homework\\week1\\lesson1\\problem2\\Hello.xlass"));
        byte[] bs = new byte[inputStream.available()];
        inputStream.read(bs);
        for (int i = bs.length - 1; i >= 0; i--) {
            bs[i] = (byte) ((byte) 255 - bs[i]);
        }
        Class clazz = new HelloClassLoader().defind(bs);
        Method method = clazz.getMethod("hello");
        method.invoke(clazz.newInstance());
    }

    private Class defind(byte[] bs) {
        return defineClass("Hello", bs, 0, bs.length);
    }
}
