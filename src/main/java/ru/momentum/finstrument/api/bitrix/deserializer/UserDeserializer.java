package ru.momentum.finstrument.api.bitrix.deserializer;

import com.google.gson.*;
import ru.momentum.finstrument.mvc.model.entity.User;

import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) json;
        int department = Integer.parseInt(jsonObject.get("UF_DEPARTMENT").getAsString());
        int ID = jsonObject.get("ID").getAsInt();
        boolean isActive = jsonObject.get("ACTIVE").getAsBoolean();
        return new User(ID, department, isActive);
    }
}
