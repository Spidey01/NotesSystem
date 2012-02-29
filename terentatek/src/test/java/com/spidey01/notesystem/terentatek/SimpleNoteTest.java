package com.spidey01.notessystem.terentatek;

import org.junit.*;

public final class SimpleNoteTest {

    private SimpleNoteApi mApi;

    @Before
    public void setup() {
        mApi = new SimpleNote("Foo@bar.bork", "1234");
    }

    @Test
    public void version() {
        Assert.assertTrue(mApi.version.startsWith("2"));
    }

}



