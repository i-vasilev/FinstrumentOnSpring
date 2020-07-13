package ru.momentum.finstrument.api.bitrix.deserializer;

import com.google.gson.*;
import ru.momentum.finstrument.core.entity.Employee;

import java.lang.reflect.Type;

/**
 * Deserializer for json Employee entity.
 */
public class EmployeeDeserializer implements JsonDeserializer<Employee> {
    @Override
    public Employee deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) json;
        int department = Integer.parseInt(jsonObject.get("UF_DEPARTMENT").getAsString());
        int ID = jsonObject.get("ID").getAsInt();
        boolean isActive = jsonObject.get("ACTIVE").getAsBoolean();
        return new Employee(ID, department, isActive);
    }
}
