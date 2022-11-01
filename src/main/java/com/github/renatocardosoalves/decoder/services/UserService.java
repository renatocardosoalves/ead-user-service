package com.github.renatocardosoalves.decoder.services;

import com.github.renatocardosoalves.decoder.models.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> findAll();

}
