package com.spidey01.notessystem.sncli;

import com.spidey01.notessystem.terentatek.*;

class Main {

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
        } else {
            System.out.println("Unrecognized command: " + a);
            help();
        }
    }

    public static void help() {
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

    public static void list() {
        System.out.println("list() called");
    }

    public static void read(String key) {
        assert key != null;
        System.out.println("read(" + key + ") called");
    }

    public static void update(String key, String[] tags) {
        assert key != null;
    }

    public static void remove(String key) {
        assert key != null;
    }

    public static void trash(String key) {
        assert key != null;
    }

    public static void untrash(String key) {
        assert key != null;
    }

}
