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
public class CameraControlActionDeserializer implements JsonDeserializer<CameraControlAction>, JsonSerializer<CameraControlAction> {
    @Override
    public CameraControlAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int typeInt = json.getAsInt();
        return CameraControlAction
                .fromInt(typeInt);
    }

    @Override
    public JsonElement serialize(CameraControlAction src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getId());
    }
}
