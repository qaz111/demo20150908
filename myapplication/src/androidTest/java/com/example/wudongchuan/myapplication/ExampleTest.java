package com.example.wudongchuan.myapplication;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;


/**
 * Created by wudongchuan on 2015/6/24.
 */
@SmallTest
public class ExampleTest extends InstrumentationTestCase {
    @SmallTest
    public void testA() throws Exception {
        final int expected = 5;
        final int reality = 5;
        assertEquals(expected, reality);
        assertTrue(expected == reality);
        int sum = new t().add(expected, reality);
        assertTrue(sum == 10);

    }

    @Test
    public void te() {
        assertTrue(3 == 4);
    }
}