package com.spidey01.notessystem.terentatek;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

/** Simple Gson type adapter for com.spidey01.notesystem.terentatek.Mark */
public class MarkAdapter extends TypeAdapter<Mark>
{
    public Mark read(JsonReader reader)
        throws IOException
    {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }

        /* get the data and make a Mark */
        return new Mark(reader.nextString());
    }

    public void write(JsonWriter writer, Mark value)
        throws IOException
    {
        if (value == null) {
            writer.nullValue();
            return;
        }
        /* do writer.value(...) with data from the Mark value */
        writer.value(value.toString());
    }
}

