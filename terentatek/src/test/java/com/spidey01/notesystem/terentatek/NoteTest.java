package com.spidey01.notessystem.terentatek;

import org.junit.*;
import com.google.gson.Gson;

public final class NoteTest {

    @Ignore("To be implemented")
    @Test
    public void jsonToNote() {
        Note n = Note.fromJson("static json data");

        // test with Assert.assertEquals([msg,] expected, actual);
    }


    private static String sGsonOutputFornoteToJson = "{\"key\":\"invalid\",\"deleted\":0,\"syncnum\":0,\"version\":0,\"minversion\":0,\"content\":\"this is a test\"}";

    @Test
    public void noteToJson() {

        // we need a Simplenote instance for the test to be possible
        Note n = Simplenote.note("invalid");
        n.content = "this is a test";
        Assert.assertEquals(Note.toJson(n), sGsonOutputFornoteToJson);
    }

}

