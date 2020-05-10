package ru.momentum.finstrument.api.bitrix.data;

import java.util.List;

public class ListUsers {
    private final List<User> users;

    public ListUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
