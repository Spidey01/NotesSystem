package com.spidey01.notessystem.terentatek;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

// TODO: We can probably use CookieManager/CookieHandler/HttpCookie from
//       java.net to deal with login issues instead of doing it in the URLs.
//       The Android developers reference for HttpURLConnection explains.
//
// TODO: specifying connection and read/write time outs for HttpURLConnection.
//
class HttpClient {

    static String get(URL url, String charsetName)
        throws IOException
    {
        String rv = null;
        HttpURLConnection remote = (HttpURLConnection)url.openConnection();

        try {
            InputStream in = new BufferedInputStream(remote.getInputStream());
            if (!url.getHost().equals(remote.getURL().getHost())) {
                // TODO: We were redirected.
                //
                // Perhaps the user just connected to an open WiFi network that
                // requires accepting TOS, registering at the hotel desk, etc.
                // How should this be handled?
                return null;
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream(64);
            int c;
            while((c = in.read()) != -1) {
                buffer.write((byte)c);
            }
            // TODO carsetName should be a method parameter or we should
            //      (ugh) do remote.getHeader("Content-Type") and look for
            //      ;\s*charset=(.*), and do a translation to Javaese -- then
            //      default to ISO-8859-1. One is NJ and one is MIT, well my
            //      momma is from NJ and I'm from UNIX!
            //
            rv = new String(buffer.toByteArray(), charsetName );

        } finally {
            remote.disconnect();
        }

        return rv;
    }

    static String get(URL url)
        throws IOException
    {
        return get(url, "ISO-8859-1");
    }

    // not tested yet
    static String post(URL url, String data, String responseCharSetName)
        throws IOException
    {
        String rv = null;
        HttpURLConnection remote = (HttpURLConnection)url.openConnection();

        try {

            // post data
            {
                remote.setDoOutput(true);
                remote.setFixedLengthStreamingMode(data.length());
                OutputStream out = new BufferedOutputStream(
                        remote.getOutputStream()
                );
                out.write(data.getBytes());
            }
            
            // read result of post for return value
            {
                // We could also use a getContent() and a BufferedReader to get our
                // return value as a String pretty easy...but I don't know how to
                // control e.g. encoding issues without doing it in bytes.
                //
                InputStream in = new BufferedInputStream(remote.getInputStream());
                if (!url.getHost().equals(remote.getURL().getHost())) {
                    // TODO: We were redirected. See TODO in get().
                    return null;
                }

                ByteArrayOutputStream buffer = new ByteArrayOutputStream(64);
                int c;
                while((c = in.read()) != -1) {
                    buffer.write((byte)c);
                }
                rv = new String(buffer.toByteArray(), responseCharSetName);
            }
        } finally {
            remote.disconnect();
        }

        return rv;
    }

    static String post(URL url, String data)
        throws IOException
    {
        return post(url, data, "ISO-8859-1");
    }
}

