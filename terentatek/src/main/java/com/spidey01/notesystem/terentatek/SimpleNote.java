package com.spidey01.notessystem.terentatek;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

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
        if (!login(email, password)) {
            throw new RuntimeException("Unable to login as \""+email+"\"");
        }
    }

    /** Logs into SimpleNote and makes the API available */
    public boolean login(String email, String password) {
        mEmail = email;
        mPassword = password;
        return login();
    }

    public boolean login() {
        try {
            URL url = new URL(mBaseUrl+"/api/login");
            mToken = HttpClient.post(url, Utils.base64("email="+mEmail + "&password="+mPassword));
        } catch(MalformedURLException e) {
            // TODO: better handling of this.
            System.err.println("MalformedUrlException in SimpleNote.login(): " + e.getMessage());
        } catch(IOException e) {
            System.err.println("IOException in SimpleNote.login(): " + e.getMessage());
        }

        if (mToken == null || mToken.isEmpty()) {
            mIsLoggedIn = false;
        } else {
            mIsLoggedIn = true;
        }

        return mIsLoggedIn;
    }

    // Date to time_t -> d.getTime();

    public NoteIndex index(int length, Mark mark, Date since)
    {
        return doIndex("/index?length="+length+"&mark="+mark+"&since="+since.getTime());
    }

    public NoteIndex index(int length, Mark mark)
    {
        return doIndex("/index?length="+length+"&mark="+mark);
    }

    public NoteIndex index(int length)
    {
        if (!mIsLoggedIn) System.err.println("Test it");

        return doIndex("/index?length="+length);
    }

    public Note note(NoteMetaData meta)
    {
        return note(meta.key);
    }

    public Note note(String key)
    {
        
        try {
           URL url = new URL(mApi2Url+"/data/"+key+"?email="+mEmail+"&auth="+mToken);
           return Utils.getGson().fromJson(doGet(url), Note.class);
        } catch(MalformedURLException e) {
            System.err.println("MalformedUrlException in SimpleNote.note(): " + e.getMessage());
            throw new Error("Invalid URL used within terentatek");
        }

       // String url = " /data/"+key;
    }

    public NoteMetaData[] list()
    {
        List<NoteMetaData> nl = new ArrayList<NoteMetaData>(128);

        NoteIndex ni = index(100);
        nl.addAll(Arrays.asList(ni.data));

        while (ni.mark != null) {
            ni = index(100, ni.mark);
            nl.addAll(Arrays.asList(ni.data));
        }

        /* less repetitive typing at the cost of a temp' object
        NoteIndex ni = new NoteIndex();
        do {
            ni = index(100, ni.mark);
            nl.addAll(Arrays.asList(ni.data));
        } while (ni.mark != null);
        */

        return nl.toArray(new NoteMetaData[0]);
    }

    /** Do the index call for url
     *
     * @param url String to pass to makeUrl
     */
    protected NoteIndex doIndex(String url)
    {
        return Utils.getGson().fromJson(doGet(url), NoteIndex.class);
    }

    /*
     * N.b. if you need ?email=...&auth=... instead of what makeUrl() gives.
     * Then this method is for you!
     */
    protected String doGet(String url)
    {
        try {
            return doGet(makeUrl(url));
        } catch(MalformedURLException e) {
            System.err.println("MalformedUrlException in SimpleNote.doGet(): " + e.getMessage());
            throw new Error("Invalid URL used within terentatek");
        }
    }

    protected String doGet(URL url)
    {
        String json = "";
        try {
            json = HttpClient.get(url);
        } catch(IOException e) {
            System.err.println("IOException in SimpleNote.doGet(): " + e.getMessage());
        }

        return json;
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
            throw new RuntimeException("Must be logged in before SimpleNote.makeUrl!");
        }

        return new URL(mApi2Url+what+"&email="+mEmail+"&auth="+mToken);
    }

}

