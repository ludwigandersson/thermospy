package com.luan.thermospy.android.core.pojo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by ludde on 15-02-01.
 */
public class ServerStatusSerializer implements JsonDeserializer<ServerStatus>, JsonSerializer<ServerStatus> {
    @Override
    public ServerStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String typeInt = json.getAsString();
        return ServerStatus
                .parseString(typeInt);
    }

    @Override
    public JsonElement serialize(ServerStatus src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getId());
    }
}
