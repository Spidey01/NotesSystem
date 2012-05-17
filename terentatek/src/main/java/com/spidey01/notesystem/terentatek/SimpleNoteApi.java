package com.spidey01.notessystem.terentatek;

/** Simple interface to the SimpleNote API.
 */
public interface SimpleNoteApi {

    /** SimpleNote API version implemented */
    public static final String version = "2.1.4";

    public boolean login(String email, String password);
}

