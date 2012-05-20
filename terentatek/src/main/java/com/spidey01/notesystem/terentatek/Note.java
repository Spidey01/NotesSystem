package com.spidey01.notessystem.terentatek;

import com.google.gson.Gson;

import java.util.Date;
import java.util.Collection;

/** Thin data class implementing SimpleNote API v2 JSON objects.
 *
 * This is designed to operate as close to the JSON API as possible, the usual
 * getX/setX conventions of Java programs are ignored in order to match the
 * API rather than abstract it.
 */
public class Note {

    private static Gson sGson;

    public final String key;
    public int deleted;
    public /*Date*/double modifydate;
    public /*Date*/double createddate;
    /** read only. */
    public long syncnum;
    /** read only. */
    public long version;
    /** read only. */
    public long minversion;
    /** read only. */
    public String sharekey;
    /** read only. */
    public String publishkey;

    public Collection<String> systemtags;
    public Collection<String> tags;
    public String content;
    
    public Note(String key) {
        this.key = key;
    }

    public static Note fromJson(String json) {
        return getGson().fromJson(json, Note.class);
    }
    
    public static String toJson(Note note) {
        return getGson().toJson(note);
    }

    private static Gson getGson() {
        if (sGson == null) {
            sGson = new Gson();
        }

        return sGson;
    }


}

