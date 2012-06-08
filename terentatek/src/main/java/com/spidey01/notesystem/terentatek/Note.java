package com.spidey01.notessystem.terentatek;

import java.util.Date;
import java.util.Collection;

/** Thin data class implementing SimpleNote API v2 JSON objects.
 *
 * This is designed to operate as close to the JSON API as possible, the usual
 * getX/setX conventions of Java programs are ignored in order to match the
 * API rather than abstract it.
 */
public class Note extends NoteMetaData {

    /** read only. */
    public String sharekey;
    /** read only. */
    public String publishkey;

    public String content;
    
    public static Note fromJson(String json) {
        return Utils.getGson().fromJson(json, Note.class);
    }
    
    public static String toJson(Note note) {
        return Utils.getGson().toJson(note);
    }

}

