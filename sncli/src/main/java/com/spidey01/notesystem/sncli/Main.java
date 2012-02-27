package com.spidey01.notessystem.sncli;

import com.spidey01.notessystem.terentatek.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

class Main {

    private static Properties mProperties;

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
            try {
                mProperties.load(
                        new FileInputStream(getConfigurationFileName()));
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return mProperties;
    }

    static void help() {
        String m = "sncli {command} [options]\n\n" +
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
    }

    static void read(String key) {
        assert key != null;

        Note n = new Note(key);
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
    
    static void login(String email) {
        String password = null;
        BufferedReader user = 
            new BufferedReader(new InputStreamReader(System.in));

        try { 
            if (email == null) {
                System.out.print("Enter Simplenote e-mail: ");
                email = user.readLine();
            }

            System.out.print("Enter Simplenote password: ");
            password = user.readLine();
        } catch (IOException e) {
            System.err.println(e);
        }

        Properties p = getProperties();
        if (p == null) {
            // TODO make ~/.local/share/sncli.properties
            System.err.println("Can't find settings file, sorry mac");
            System.exit(127);
        }

        p.setProperty("email", email);
        p.setProperty("password", password);
        try {
            FileOutputStream config =
                new FileOutputStream(getConfigurationFileName());
            p.store(config, null);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
