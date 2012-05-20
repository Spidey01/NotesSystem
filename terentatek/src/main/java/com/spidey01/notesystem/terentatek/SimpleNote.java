package com.spidey01.notessystem.terentatek;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/** Simple access to the SimpleNote API.
 */
public class SimpleNote
    implements SimpleNoteApi
{

    private String mEmail;
    private String mPassword;
    private String mToken;
    private static String mBaseUrl = "https://simple-note.appspot.com";
    private static String mApi2Url = mBaseUrl + "/api2";
    private boolean mIsLoggedIn = false;

    public SimpleNote() {
    }

    public SimpleNote(String email, String password) {
        mEmail = email;
        mPassword = password;

        login(mEmail, mPassword);

        try {
        System.err.println("Get of index: " + HttpClient.get(makeUrl("/index?length=1")));
        } catch(Exception e) { System.err.println("WTF: "+e.getMessage()); }
    }

    /** Logs into SimpleNote and makes the API available */
    public boolean login(String email, String password) {
        try {
            URL url = new URL(mBaseUrl+"/api/login");
            mToken = HttpClient.post(url, Utils.base64("email="+mEmail + "&password="+mPassword));
        } catch(MalformedURLException e) {
            // TODO: better handling of this.
            System.err.println("MalformedUrlException in Simplenote.Simplenote(): " + e.getMessage());
        } catch(IOException e) {
            System.err.println("IOException in Simplenote.Simplenote(): " + e.getMessage());
        }

        if (mToken == null || mToken.isEmpty()) {
            mIsLoggedIn = false;
        } else {
            mIsLoggedIn = true;
        }

        return mIsLoggedIn;
    }

    public void index(int length)
    {
        if (!mIsLoggedIn) System.err.println("Test it");

        System.err.println("Doing get");
        String json = "";
        try {
            URL url = makeUrl("/index?length="+length);
            json = HttpClient.get(url);
        } catch(MalformedURLException e) {
            System.err.println("MalformedUrlException in Simplenote.index(): " + e.getMessage());
        } catch(IOException e) {
            System.err.println("IOException in Simplenote.index(): " + e.getMessage());
        }

        if (json != null) {
            System.err.println("Making NoteIndex");
            NoteIndex p = Utils.getGson().fromJson(json,NoteIndex.class);

            System.err.println("p.data[0].key = \"" + p.data[0].key + "\"");
            System.err.println("p.time.toString() = " + p.time.toString());
        } else {
            System.err.println("String json == null :-(;");
        }
    }

    /** Makes a URL for a SimpleNote API call
     *
     * Note that for this to work, you must be logged in or a RuntimeException
     * will result.
     *
     * @param what a snippet like "/index" or "/data/...."
     * @return a URL suitable for the API call associated with what.
     */
    protected URL makeUrl(String what)
        throws MalformedURLException
    {
        if (!mIsLoggedIn) {
            throw new RuntimeException("Must be logged in to SimpleNote.makeUrl!");
        }

        return new URL(mApi2Url+what+"&email="+mEmail+"&auth="+mToken);
    }

    class NoteIndex {
        /* Get of index (pretty printed/edited): {
            "count":1,
            "data":[
                {
                    "modifydate": "1336661715.656000",
                    "tags": ["Android", "Backups"],
                    "deleted": 0,
                    "createdate": "1324770139.417514",
                    "systemtags": ["pinned", "markdown"],
                    "version": 19,
                    "syncnum": 12,
                    "key": "short hashed string",
                    "minversion": 9
                }
            ],
            "time":"1337545449.688046",
            "mark":"long hashed string"
           }
        */
        public long count;
        public Note[] data;
        public Date time;
        public String mark;
    }
}

