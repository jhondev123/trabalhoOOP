package com.TrabalhoOOP.Controllers;

import com.TrabalhoOOP.Entities.User;

public class UserController {
    public User CreateUser(String name, String nickName)
    {
        return new User(name, nickName);
    }
}
