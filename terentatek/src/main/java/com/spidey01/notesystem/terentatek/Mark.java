package com.spidey01.notessystem.terentatek;

/** Cheesy class to hopefully make it harder to mix up Mark's and String's.
 *
 * Because Simplenote uses a long hash like string for the mark.
 */
final class Mark {

    private String mMark;

    public Mark(String mark)
    {
        mMark = mark;
    }

    @Override
    public String toString()
    {
        return mMark;
    }

}
