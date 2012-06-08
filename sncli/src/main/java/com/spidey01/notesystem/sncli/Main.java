package com.spidey01.notessystem.sncli;

import com.spidey01.notessystem.terentatek.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

class Main {

    private static Properties mProperties;
    private static SimplenoteApi mSimplenote;

    /** dispatches sncli {..} to Main.{...} */
    public static void main(String[] args) {
        if (args.length <= 0 || args[0] == "help") {
            help();
            return;
        }

        String a = args[0];

        String key = null;
        if (args.length > 1) {
            key = args[1];
        }

        if (a.equals("ls") || a.equals("list")) {
            list();
        } else if (a.equals("read")) {
            read(key);
        } else if (a.equals("update")) {
            update(key, null);
        } else if (a.equals("delete")
                    || a.equals("remove")
                    || a.equals("rm"))
        {
            remove(key);
        } else if (a.equals("trash")) {
            trash(key);
        } else if (a.equals("untrash")) {
            untrash(key);
        } else if (a.equals("tag")) {
        } else if (a.equals("login")) {
            login(key);
        } else if (a.equals("debug")) {
            debug();
        } else {
            System.out.println("Unrecognized command: " + a);
            help();
        }
    }

    static String getConfigurationFileName() {
        String ps = System.getProperty("file.separator");
        String root = System.getenv("XDG_DATA_HOME");

        if (root == null) {
            root = System.getProperty("user.home")
                   + ps + ".local" + ps + "share";
        }

        return root + ps + "sncli.properties";
    }

    // static Preferences getPreferences() {
        // return Preferences.userNodeForPackage(Main.class);
    // }

    static Properties getProperties() {
        if (mProperties == null) {
            mProperties = new Properties();
            String n = getConfigurationFileName();

            try {
                // create file if !exists
                File f = new File(n);
                new File(f.getParent()).mkdirs();
                f.createNewFile();

                mProperties.load(new FileInputStream(n));
            } catch (IOException e) {
                System.err.println("IOException in Main.getProperties(): " + e.getMessage());
            }
        }
        return mProperties;
    }

    static void help() {
        String m = "sncli {command} [options]\n\n" +
                   "    sncli login [email]\n" +
                   "    sncli {ls || list}\n" +
                   "    sncli read {note.key}\n" +
                   "    sncli update {note.key} [tags ...]\n" +
                   "    sncli {delete || remove} {note.key}\n" +
                   "    sncli trash {note.key}\n" +
                   "    sncli untrash {note.key}\n" +
                   "    sncli tag {note.key} add [tags ...]\n" +
                   "    sncli tag {note.key} set [tags ...]\n" +
                   "    sncli tag {note.key} {remove || delete} [tags ...]";

        System.out.println(m);
    }

    static void list() {
        Main.login("BigBoss1964@gmail.com");
        // for (Note n : mSimplenote.list()) {
        // Note[] nl = mSimplenote.list();
        NoteMetaData[] nl = mSimplenote.list();
        // for (Note n : nl) {
        for (NoteMetaData n : nl) {
            System.out.println(n.key+"\t"+n.version+"\t"+n.modifydate);
        }
    }

    static void read(String key) {
        assert key != null;

        // Note n = new Note(key);
    }

    static void update(String key, String[] tags) {
        assert key != null;
    }

    static void remove(String key) {
        assert key != null;
    }

    static void trash(String key) {
        assert key != null;
    }

    static void untrash(String key) {
        assert key != null;
    }

    static String promptForLine(String prompt,
                                InputStream in,
                                PrintStream promptTo)
        throws IOException
    {
        String result = null;

        BufferedReader user = 
            new BufferedReader(new InputStreamReader(in));

        promptTo.print(prompt);
        result = user.readLine();
        
        return result;
    }

    static String promptForLine(String prompt)
        throws IOException
    {
        return promptForLine(prompt, System.in, System.out);
    }

    static boolean setPropertyInteractively(Properties p,
                                           String key,
                                           String prompt)
    {
            try {
                p.setProperty(key, promptForLine(prompt));
            } catch (IOException e) {
                System.err.println("IOException in Main.setPropertyInteractively(): "+ e.getMessage());
                p.setProperty(key, null);;
                return false;
            }
            return true;
    }
    
    // TODO: error check setPropertyInteractively() calls.
    static void login(String email) {

        Properties p = getProperties();
        if (p == null) {
            System.err.println("Can't find settings file, sorry mac");
            System.exit(127);
        }

        if (email != null) {
            p.setProperty("email", email);
        }
        if (!p.containsKey("email")) {
            setPropertyInteractively(p, "email",
                                     "Enter Simplenote e-mail: ");
        }

        if (!p.containsKey("password")) {
            setPropertyInteractively(p, "password",
                                     "Enter Simplenote password: ");
        }

        try {
            FileOutputStream config =
                new FileOutputStream(getConfigurationFileName());
            p.store(config, null);
        } catch (Exception e) {
            System.err.println("Exception in Main.login() at FileOutputStream config: " + e.getMessage());
        }

        mSimplenote = new Simplenote(p.getProperty("email"),
                                     p.getProperty("password"));
    }

    /* place to put code crap for debugging */
    static void debug() {
        Main.login("BigBoss1964@gmail.com");

        // NoteMetaData[] nl = mSimplenote.list();
        NoteMetaData[] nl = mSimplenote.index(5).data;

        for (NoteMetaData n : nl) {
            Note note = mSimplenote.note(n);
            System.out.println(note.content.substring(0, 16));
        }
    }
}
