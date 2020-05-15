package ru.momentum.finstrument.rest.controller;

import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.momentum.finstrument.api.bitrix.data.ListUsers;
import ru.momentum.finstrument.mvc.model.entity.User;
import ru.momentum.finstrument.rest.responce.MessageResponce;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestServiceController {
    @PostMapping("/add-users")
    public MessageResponce addUsers(HttpServletRequest request) {
        final String users = request.getHeader("users");
        final GsonBuilder gsonBuilder = new GsonBuilder();
        ListUsers usersList = gsonBuilder.create().fromJson(users, ListUsers.class);
        return new MessageResponce(String.valueOf(usersList.getUsers().get(0).getID()));
    }
}
