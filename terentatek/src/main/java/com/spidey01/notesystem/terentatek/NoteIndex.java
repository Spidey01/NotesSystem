package com.spidey01.notessystem.terentatek;

import java.util.Date;

public class NoteIndex {
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

    public int count;
    public NoteMetaData[] data;
    public Date time;
    public Mark mark;
}
