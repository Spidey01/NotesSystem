package com.spidey01.notessystem.terentatek;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.iharder.Base64;

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
    }

    /** Logs into SimpleNote and makes the API available */
    public boolean login(String email, String password) {
        try {
            URL url = new URL(mBaseUrl+"/api/login");
            mToken = HttpClient.post(url, base64("email="+mEmail + "&password="+mPassword));
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

    protected String base64(String what) {
        return Base64.encodeBytes(what.getBytes());
    }

}

