package com.spidey01.notessystem.terentatek;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.util.Date;
// import java.text.DateFormat;
import java.lang.reflect.Type;

/** A Gson type adapter for java.util.Date
 */
class DateTypeConverter
    implements JsonSerializer<Date>, JsonDeserializer<Date>
{

    @Override
    public JsonElement serialize(Date src, Type srcType, JsonSerializationContext context) {
        // convert ms to s and I assume, we have to try for division error?
        return new JsonPrimitive(src.getTime()/1000);
    }

    @Override
    public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        // return new DateFormat().parse(json.getAsString());
        return new Date(json.getAsLong());
    }
}

