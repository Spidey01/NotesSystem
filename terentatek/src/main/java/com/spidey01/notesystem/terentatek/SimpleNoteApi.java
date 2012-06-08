package com.spidey01.notessystem.terentatek;

import java.util.Date;

/** Simple interface to the SimpleNote API.
 */
public interface SimpleNoteApi {

    /** SimpleNote API version implemented */
    public static final String version = "2.1.4";

    public boolean login(String email, String password);

    /** Get a NoteIndex.
     *
     * @param length The number of notes to return.
     *               Simpenote limits this to 100 but intentionall no checking is done here.
     * @param mark   A "Bookmark" to continue querying successive notes from a previous Index.
     * @param since  Only return notes since this date.
     */
    public NoteIndex index(int length, Mark mark, Date since);
    public NoteIndex index(int length, Mark mark);
    public NoteIndex index(int length);


    /** Convenience method to return a array of all Note's.
     *
     * This calls index() multiple times and returns the concatenation of their
     * data elements. It is intended more to serve as a source example of how
     * to use the index() method then for production code.
     *
     * If your code just wants a lump sum list of notes, it will probably do fine.
     */
    public Note[] list();
}

