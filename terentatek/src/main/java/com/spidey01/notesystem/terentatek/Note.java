package com.spidey01.notessystem.terentatek;

import java.util.Date;
import java.util.Collection;

/** Thin data class implementing SimpleNote API v2 JSON objects.
 *
 * This is designed to operate as close to the JSON API as possible, the usual
 * getX/setX conventions of Java programs are ignored in order to match the
 * API rather than abstract it.
 */
public class Note {

    public final String key;
    public int deleted;
    public Date modifydate;
    public Date createddate;
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
        return Utils.getGson().fromJson(json, Note.class);
    }
    
    public static String toJson(Note note) {
        return Utils.getGson().toJson(note);
    }

}

