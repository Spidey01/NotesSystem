package com.spidey01.notessystem.terentatek;

import java.util.Date;
import java.util.Collection;

public class NoteMetaData {

    /** read only. */
    public /*final*/ String key;
    public int deleted;
    public Date modifydate;
    public Date createddate;
    /** read only. */
    public long syncnum;
    /** read only. */
    public long version;
    /** read only. */
    public long minversion;

    public Collection<String> systemtags;
    public Collection<String> tags;
    
    public static NoteMetaData fromJson(String json) {
        return Utils.getGson().fromJson(json, NoteMetaData.class);
    }
    
    public static String toJson(NoteMetaData note) {
        return Utils.getGson().toJson(note);
    }

}

