package com.spidey01.notessystem.terentatek;

import org.junit.*;

public final class SimplenoteTest {

    private SimplenoteApi mApi;

    @Before
    public void setup() {
        mApi = new Simplenote("Foo@bar.bork", "1234");
    }

    @Test
    public void version() {
        Assert.assertTrue(mApi.version.startsWith("2"));
    }

}



