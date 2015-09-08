package com.example.wudongchuan.myapplication;
import junit.framework.TestSuite;
import junit.framework.Test;
import junit.textui.TestRunner;

/**
 * 测试单元
 * File: TestAll.java
 * User: leizhimin
 * Date: 2008-3-13 16:17:10
 */
public class TestAll extends TestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite("TestSuite Test");
//        suite.addTestSuite(MainTest.class);
        return suite;
    }
    public static void main(String args[]){
        TestRunner.run(suite());
    }
}