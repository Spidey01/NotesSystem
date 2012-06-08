package com.spidey01.notessystem.terentatek;

import org.junit.*;

public final class UtilsTest {

    @Test
    public void getJson() {
        Assert.assertNotNull(Utils.getGson());
    }

    @Test
    public void base64() {
        Assert.assertEquals(Utils.base64("Test"), "VGVzdA==");
    }

}

