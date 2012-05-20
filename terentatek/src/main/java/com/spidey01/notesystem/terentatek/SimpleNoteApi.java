package com.spidey01.notessystem.terentatek;

/** Simple interface to the SimpleNote API.
 */
public interface SimpleNoteApi {

    /** SimpleNote API version implemented */
    public static final String version = "2.1.4";

    public boolean login(String email, String password);
    /* TODO:
     *
     *  make mark a simple final Mark class so users don't get real access.
     *  make since whatever type gson gives us for stuff like *date fields.
     */
    public void index(int length);
}

