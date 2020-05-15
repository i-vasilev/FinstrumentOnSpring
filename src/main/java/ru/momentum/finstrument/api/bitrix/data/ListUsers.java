package ru.momentum.finstrument.api.bitrix.data;

import com.google.gson.annotations.SerializedName;
import ru.momentum.finstrument.mvc.model.entity.User;

import java.util.List;

public class ListUsers {
    @SerializedName("result")
    private final List<User> users;

    public ListUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
