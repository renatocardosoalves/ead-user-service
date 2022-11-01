package com.github.renatocardosoalves.decoder.services;

import com.github.renatocardosoalves.decoder.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserModel> findAll();

    Optional<UserModel> findById(UUID userId);

}
