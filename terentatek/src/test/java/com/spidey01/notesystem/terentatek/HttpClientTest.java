package com.spidey01.notessystem.terentatek;

import org.junit.*;
import java.io.IOException;
import java.net.URL;

public final class HttpClientTest {

    @Test
    public void get() {
        // Assert.assertEquals(Note.toJson(n), sGsonOutputFornoteToJson);
        try {
            String s = HttpClient.get(new URL("http://spidey01.com/"));

            Assert.assertTrue(s.startsWith("<html>") ||
                              s.startsWith("<!DOCTYPE html"));
        } catch(IOException e) {
            System.err.println(e);
        }
    }

}


