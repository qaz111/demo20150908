package com.example.wudongchuan.myapplication;


import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by wudongchuan on 2015/6/24.
 */
public class MainTest extends t{
    @Test
    public void te() {
        int a=new t().add(3,4);
        assertTrue(7 == a);
        assertTrue(7 == a);
    }
    @Test
    public void t2() {
        int a=new t().add(3,4);
        assertTrue(7 == a);
        assertTrue(17 == a);
    }
    public static void main(String args[]){
        new MainTest().te();
    }
}
