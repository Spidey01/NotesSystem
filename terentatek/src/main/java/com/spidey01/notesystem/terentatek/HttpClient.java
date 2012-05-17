package com.spidey01.notessystem.terentatek;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
// TODO carsetName like params should be a method parameter or we should
//      (ugh) do remote.getHeader("Content-Type") and look for ;\s*charset=(.*),
//      and do a translation to Javaese -- then default to ISO-8859-1. One is
//      NJ and one is MIT, well my momma is from NJ and I'm from UNIX!
//
//
class HttpClient {

    /** Response code of the last request
     *
     * this is obviously not thread safe.
     */
    static int LastResponseCode;


    static String get(URL url)
        throws IOException
    {
        return get(url, "ISO-8859-1");
    }

    static String get(URL url, String charsetName)
        throws IOException
    {
        /*
        String rv = null;
        URLConnection remote = url.openConnection();
        remote.setRequestProperty("User-Agent", "terentatek");

        InputStream in = new BufferedInputStream(remote.getInputStream());
        if (!url.getHost().equals(remote.getURL().getHost())) {
            // TODO: We were redirected.
            //
            // Perhaps the user just connected to an open WiFi network that
            // requires accepting TOS, registering at the hotel desk, etc.
            // How should this be handled?
            return null;
        }
        LastResponseCode = ((HttpURLConnection)remote).getResponseCode();

        return rv;
        */

        URLConnection remote = setupConnection(url, "text/plain");
        String rv = streamToString(remote.getInputStream());
        LastResponseCode = ((HttpURLConnection)remote).getResponseCode();
        return rv;
    }

    static String post(URL url, String data)
        throws IOException
    {
        return post(url, data, "ISO-8859-1");
    }

    static String post(URL url, String data, String responseCharSetName)
        throws IOException
    {
        URLConnection remote = setupConnection(url, "text/plain");
        remote.setDoOutput(true);
        //remote.setRequestProperty("Content-Length", data.getBytes().length.toString());
        OutputStream out = remote.getOutputStream();
        out.write(data.getBytes());
        out.close();

        LastResponseCode = ((HttpURLConnection)remote).getResponseCode();

        return streamToString(remote.getInputStream(), responseCharSetName);
    }
    

    static protected String streamToString(InputStream in) 
        throws IOException
    {
        return streamToString(in, "ISO-8859-1");
    }

    static protected String streamToString(InputStream in, String charsetName) 
        throws IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(64);
        int c;
        while((c = in.read()) != -1) {
            buffer.write((byte)c);
        }

        return new String(buffer.toByteArray(), charsetName );
    }

    static protected URLConnection setupConnection(URL url) {
        return setupConnection(url, "");
    }

    static protected URLConnection setupConnection(URL url, String contentType) {
        URLConnection c;
        try {
            c = url.openConnection();
        } catch(IOException e) {
            System.err.println("IOException in HttpClient.setupConnection(): " + e.getMessage());
            throw new RuntimeException("URL.openConnection() threw IOException", e);
        }

        if (!contentType.isEmpty()) {
            c.setRequestProperty("Content-Type", contentType);
        }
        c.setRequestProperty("User-Agent", "terentatek");
        
        return c;
    }
}

