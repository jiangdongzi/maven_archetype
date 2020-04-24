package com.java_virM;




public class LoaderTest {

    public static void main(String[] args) {
        ClassLoader objLoader = LoaderTest.class.getClassLoader();
        System.out.println(objLoader);
        System.out.println(Thread.currentThread().getContextClassLoader());
    }

}
