package com.spidey01.notessystem.terentatek;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

final class Utils {

    private static Gson sGson;

    static Gson getGson() {
        if (sGson == null) {
            GsonBuilder b = new GsonBuilder();
            // Override GSon's internal DateTypeAdapter
            b.registerTypeAdapter(Date.class, new DateAdapter());
            sGson = b.create();
        }

        return sGson;
    }
}

